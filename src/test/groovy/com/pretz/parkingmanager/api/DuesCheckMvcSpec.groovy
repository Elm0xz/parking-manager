package com.pretz.parkingmanager.api

import com.pretz.parkingmanager.IntegrationTest
import com.pretz.parkingmanager.domain.ParkingRate
import com.pretz.parkingmanager.domain.ParkingSession
import com.pretz.parkingmanager.repository.ParkingSessionRepository
import org.junit.experimental.categories.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Category(IntegrationTest.class)
class DuesCheckMvcSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ParkingSessionRepository parkingSessionRepository

    private String testVehicleId = "BGT4025"
    private String testParkingSessionId
    private String testCurrencyCode = "PLN"
    private Timestamp startTimestamp = Timestamp.from(Instant.now()
            .minus(2, ChronoUnit.HOURS)
            .minus(45, ChronoUnit.MINUTES)
            .minus(11, ChronoUnit.SECONDS))

    def setup() {

        ParkingSession testParkingSession = ParkingSession.builder()
                .vehicleId(testVehicleId)
                .parkingRate(ParkingRate.REGULAR)
                .startTime(startTimestamp)
                .build()

        testParkingSession == parkingSessionRepository.save(testParkingSession)
        testParkingSessionId = testParkingSession.getId()
    }

    def cleanup() {
        parkingSessionRepository.deleteAll()
    }

    def "Should show dues for specified vehicle and parking session in requested currency when vehicle is still parked"() {

        given:

        Map request = [
                vehicleId       : testVehicleId,
                parkingSessionId: testParkingSessionId,
                currencyCode    : testCurrencyCode
        ]

        when:
        def result = mockMvc.perform(get('/dues/')
                .param("vehicleId", request.vehicleId)
                .param("parkingSessionId", request.parkingSessionId)
                .param("currencyCode", request.currencyCode))

        then:
        result.andExpect(status().isOk())

        and:
        result.andExpect(jsonPath('$.parkingStartTime').value(startTimestamp.toString()))
        result.andExpect(jsonPath('$.dues').value(6.00))
    }

    def "Should not show dues for specified vehicle and parking session in requested currency if vehicle is not parked and return code 400 (bad request)"() {

        given:

        Map request = [
                vehicleId       : "TRE7784",
                parkingSessionId: "56",
                currencyCode    : testCurrencyCode
        ]

        when:
        def result = mockMvc.perform(get('/dues/')
                .param("vehicleId", request.vehicleId)
                .param("parkingSessionId", request.parkingSessionId)
                .param("currencyCode", request.currencyCode))

        then:
        result.andExpect(status().isBadRequest())
    }

    def "Should not show dues for specified vehicle and parking session in requested currency if vehicle had been parked some time ago and return code 400 (bad request)"() {

        given:
        addStoppedOneHourAgoInfo(testVehicleId)

        Map request = [
                vehicleId       : testVehicleId,
                parkingSessionId: testParkingSessionId,
                currencyCode    : testCurrencyCode
        ]

        when:
        def result = mockMvc.perform(get('/dues/')
                .param("vehicleId", request.vehicleId)
                .param("parkingSessionId", request.parkingSessionId)
                .param("currencyCode", request.currencyCode))

        then:
        result.andExpect(status().isBadRequest())

    }

    def "Should show dues for specified vehicle and parking session when vehicle has just stopped parking"() {

        given:
        stopParkingSession(testParkingSessionId)

        Map request = [
                vehicleId       : testVehicleId,
                parkingSessionId: testParkingSessionId,
                currencyCode    : testCurrencyCode
        ]

        when:
        def result = mockMvc.perform(get('/dues/{id}', request.parkingSessionId)
                .param("currencyCode", request.currencyCode))

        then:
        result.andExpect(status().isOk())

        and:
        result.andExpect(jsonPath('$.parkingStartTime').value(startTimestamp.toString()))
        result.andExpect(jsonPath('$.dues').value(6.00))
    }


    def "Should not show dues for specified vehicle and parking session in requested currency when currency code is not handled and return code 400 (bad request)"() {

        given:

        Map request = [
                vehicleId       : testVehicleId,
                parkingSessionId: testParkingSessionId,
                currencyCode    : "XYZ"
        ]

        when:
        def result = mockMvc.perform(get('/dues/')
                .param("vehicleId", request.vehicleId)
                .param("parkingSessionId", request.parkingSessionId)
                .param("currencyCode", request.currencyCode))

        then:
        result.andExpect(status().isBadRequest())

    }

    def addStoppedOneHourAgoInfo(String vehicleId) {

        ParkingSession parkingSessionToBeStopped = parkingSessionRepository.findByVehicleIdAndStopTimeIsNull(vehicleId).get()
        parkingSessionToBeStopped.setStopTime(Timestamp.from(Instant.now().minus(1, ChronoUnit.HOURS)))
        parkingSessionRepository.save(parkingSessionToBeStopped)
    }

    def stopParkingSession(String parkingSessionId) {

        ParkingSession parkingSessionToBeStopped = parkingSessionRepository.findById(Long.valueOf(parkingSessionId)).get()
        parkingSessionToBeStopped.setStopTime(Timestamp.from(Instant.now()))
        parkingSessionRepository.save(parkingSessionToBeStopped)
    }
}
