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

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Category(IntegrationTest.class)
class ParkingStartMvcSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ParkingSessionRepository parkingSessionRepository

    String testVehicleId = 'ABC1234'
    long testParkingRateId = 1

    def cleanup() {
        parkingSessionRepository.deleteAll()
    }

    def "Should start parking for provided vehicle number and return confirmation status with code 200"() {

        given:

        Map request = [
                vehicleId    : testVehicleId,
                parkingRateId: testParkingRateId
        ]

        when:
        def result = mockMvc.perform(post('/driver/start-parking').contentType(APPLICATION_JSON).content(toJson(request)))

        then:
        result.andExpect(status().isOk())

        and:
        result.andExpect(jsonPath('$.vehicleId').value(testVehicleId))
        result.andExpect(jsonPath('$.parkingSessionId').isNotEmpty())
        result.andExpect(jsonPath('$.parkingSessionId').isNumber())
        result.andExpect(jsonPath('$.timestamp').isNotEmpty())
    }

    def "Should detect that parking has already started for provided vehicle number and return code 409 (conflict)"() {

        given:
        initTestDatabaseData()

        Map request = [
                vehicleId    : testVehicleId,
                parkingRateId: testParkingRateId
        ]

        when:
        def result = mockMvc.perform(post('/driver/start-parking').contentType(APPLICATION_JSON).content(toJson(request)))

        then:
        result.andExpect(status().isConflict())
    }

    def "Should detect that parking rate id has invalid value and return code 400"() {

        given:

        Map request = [
                vehicleId    : testVehicleId,
                parkingRateId: 7
        ]

        when:
        def result = mockMvc.perform(post('/driver/start-parking').contentType(APPLICATION_JSON).content(toJson(request)))

        then:
        result.andExpect(status().isBadRequest())
    }

    def "Should detect that vehicle id passed in start request is has invalid format and return code 400"() {

        given:

        Map startRequest = [
                vehicleId    : '#$RE23',
                parkingRateId: testParkingRateId
        ]

        when:
        def startResult = mockMvc.perform(post('/driver/start-parking').contentType(APPLICATION_JSON).content(toJson(startRequest)))

        then:
        startResult.andExpect(status().isBadRequest())
    }

    private void initTestDatabaseData() {

        ParkingSession testParkingSessionEntity = ParkingSession.builder()
                .vehicleId(testVehicleId)
                .parkingRate(ParkingRate.REGULAR)
                .startTime(Timestamp.from(Instant.now()))
                .build()
        parkingSessionRepository.save(testParkingSessionEntity)
    }
}
