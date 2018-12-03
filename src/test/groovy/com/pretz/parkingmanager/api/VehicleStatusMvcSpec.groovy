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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Category(IntegrationTest.class)
class VehicleStatusMvcSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ParkingSessionRepository parkingSessionRepository

    private String testVehicleId = "TYR2345"

    def setup() {
        parkingSessionRepository.deleteAll()
    }

    def "should confirm that vehicle is on parking if parking meter was started for that vehicle"() {

        given:
        initParkingSession(testVehicleId)

        when:
        def result = mockMvc.perform(get('/vehicle-status/')
                .param("vehicleId", testVehicleId))

        then:
        result.andExpect(status().isOk())
        result.andReturn().getResponse().getContentAsString() == "true"
    }

    def "should show that vehicle has not started a parking meter"() {

        given:

        when:
        def result = mockMvc.perform(get('/vehicle-status/')
                .param("vehicleId", testVehicleId))

        then:
        result.andExpect(status().isOk())
        result.andReturn().getResponse().getContentAsString() == "false"
    }

    def initParkingSession(String testVehicleId) {

        Timestamp startTimestamp = Timestamp.from(Instant.now()
                .minus(2, ChronoUnit.HOURS)
                .minus(45, ChronoUnit.MINUTES)
                .minus(11, ChronoUnit.SECONDS))

        ParkingSession testParkingSessionEntity = ParkingSession.builder()
                .vehicleId(testVehicleId)
                .parkingRate(ParkingRate.REGULAR)
                .startTime(startTimestamp)
                .build()

        parkingSessionRepository.save(testParkingSessionEntity)
    }
}
