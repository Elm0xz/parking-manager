package com.pretz.parkingmanager.api

import com.pretz.parkingmanager.IntegrationTest
import org.junit.experimental.categories.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

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

    def "Should start parking session for provided vehicle number and return confirmation status with code 200"() {

        given:
        String testVehicleId = 'ABC1234'
        long testParkingRateId = 1

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
        String testVehicleId = 'UFO1138'
        long testParkingRateId = 1

        Map request = [
                vehicleId    : testVehicleId,
                parkingRateId: testParkingRateId
        ]

        when:
        mockMvc.perform(post('/driver/start-parking').contentType(APPLICATION_JSON).content(toJson(request)))
        def result = mockMvc.perform(post('/driver/start-parking').contentType(APPLICATION_JSON).content(toJson(request)))

        then:
        result.andExpect(status().isConflict())
    }

    def "Should detect that parking rate id has wrong value and return code 400"() {

        given:
        String testVehicleId = 'AFX2534'
        long testParkingRateId = 7

        Map request = [
                vehicleId    : testVehicleId,
                parkingRateId: testParkingRateId
        ]

        when:
        def result = mockMvc.perform(post('/driver/start-parking').contentType(APPLICATION_JSON).content(toJson(request)))

        then:
        result.andExpect(status().isBadRequest())
    }

    def "Should detect that vehicle id is has improper format and return code 400"() {

        given:
        String testVehicleId = '#$RE23'
        long testParkingRateId = 7

        Map request = [
                vehicleId    : testVehicleId,
                parkingRateId: testParkingRateId
        ]

        when:
        def result = mockMvc.perform(post('/driver/start-parking').contentType(APPLICATION_JSON).content(toJson(request)))

        then:
        result.andExpect(status().isBadRequest())
    }
}
