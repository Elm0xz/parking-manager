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

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Category(IntegrationTest.class)
class ParkingStopMvcSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ParkingSessionRepository parkingSessionRepository

    String testVehicleId = 'ZXC4572'
    long testParkingSessionId = 25

    def setup() {

        ParkingSession testParkingSessionEntity = ParkingSession.builder()
                .vehicleId(testVehicleId)
                .parkingRate(ParkingRate.REGULAR)
                .startTime(Timestamp.from(Instant.now()))
                .build()
        parkingSessionRepository.save(testParkingSessionEntity)
    }

    def cleanup() {
        parkingSessionRepository.deleteAll()
    }

    def "Should stop parking meter for provided vehicle id and return code 303 with url of created dues resource"() {
        given:

        Map request = [
                vehicleId       : testVehicleId,
                parkingSessionId: testParkingSessionId
        ]

        when:
        def result = mockMvc.perform(post('/driver/stop-parking').contentType(APPLICATION_JSON).content(toJson(request)))

        then:
        result.andExpect(status().isSeeOther())

        and:
        result.andExpect(jsonPath('$.vehicleId').value(testVehicleId))
        result.andExpect(jsonPath('$.parkingSessionId').isNotEmpty())
        result.andExpect(jsonPath('$.parkingSessionId').isNumber())
        result.andExpect(jsonPath('$.timestamp').isNotEmpty())
        //expect header to have location set sumthin sumthin
    }

    def "Should detect that parking has already stopped for provided vehicle id and return code 409 (conflict)"() {
        given:
        initTestDataWithStopTimeSet()

        Map request = [
                vehicleId       : testVehicleId,
                parkingSessionId: testParkingSessionId
        ]

        when:
        def result = mockMvc.perform(post('/driver/stop-parking').contentType(APPLICATION_JSON).content(toJson(request)))

        then:
        result.andExpect(status().isConflict())
    }

    def "Should detect that there is no vehicle to have parking stopped under provided vehicle id and return code 400"() {
        given:
        initEmptyTestData()

        Map request = [
                vehicleId       : testVehicleId,
                parkingSessionId: testParkingSessionId
        ]

        when:
        def result = mockMvc.perform(post('/driver/stop-parking').contentType(APPLICATION_JSON).content(toJson(request)))

        then:
        result.andExpect(status().isConflict())
    }

    def "Should detect that vehicle id passed in stop request is has invalid format and return code 400"() {

        given:

        Map stopRequest = [
                vehicleId       : "f4fq32g",
                parkingSessionId: testParkingSessionId
        ]

        when:
        def stopResult = mockMvc.perform(post('/driver/stop-parking').contentType(APPLICATION_JSON).content(toJson(stopRequest)))

        then:
        stopResult.andExpect(status().isBadRequest())
    }

    private void initTestDataWithStopTimeSet() {

        parkingSessionRepository.deleteAll()
        ParkingSession testParkingSessionEntity = ParkingSession.builder()
                .vehicleId(testVehicleId)
                .parkingRate(ParkingRate.REGULAR)
                .startTime(Timestamp.from(Instant.now().minus(3, ChronoUnit.HOURS)))
                .stopTime(Timestamp.from(Instant.now().minus(1, ChronoUnit.HOURS)))
                .build()
        parkingSessionRepository.save(testParkingSessionEntity)
    }

    private void initEmptyTestData() {
        parkingSessionRepository.deleteAll()
    }
}
