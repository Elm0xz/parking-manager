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
import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Category(IntegrationTest.class)
class EarningsMvcSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ParkingSessionRepository parkingSessionRepository

    def setup() {

        ParkingSession testParkingSession1 = ParkingSession.builder()
                .vehicleId("ACD1243")
                .parkingRate(ParkingRate.REGULAR)
                .startTime(Timestamp.valueOf(LocalDateTime.of(2018, 11, 10, 15, 10)))
                .stopTime(Timestamp.valueOf(LocalDateTime.of(2018, 11, 10, 16, 25)))
                .dues(BigDecimal.valueOf(3.00))
                .build()

        ParkingSession testParkingSession2 = ParkingSession.builder()
                .vehicleId("ZYU8462")
                .parkingRate(ParkingRate.REGULAR)
                .startTime(Timestamp.valueOf(LocalDateTime.of(2018, 11, 10, 12, 15)))
                .stopTime(Timestamp.valueOf(LocalDateTime.of(2018, 11, 10, 14, 22)))
                .dues(BigDecimal.valueOf(6.00))
                .build()

        ParkingSession testParkingSession3 = ParkingSession.builder()
                .vehicleId("BHG4763")
                .parkingRate(ParkingRate.REGULAR)
                .startTime(Timestamp.valueOf(LocalDateTime.of(2018, 11, 11, 16, 10)))
                .stopTime(Timestamp.valueOf(LocalDateTime.of(2018, 11, 11, 16, 35)))
                .dues(BigDecimal.valueOf(1.00))
                .build()

        ParkingSession testParkingSession4 = ParkingSession.builder()
                .vehicleId("ACD1243")
                .parkingRate(ParkingRate.REGULAR)
                .startTime(Timestamp.valueOf(LocalDateTime.of(2018, 11, 12, 17, 10)))
                .stopTime(Timestamp.valueOf(LocalDateTime.of(2018, 11, 12, 18, 53)))
                .dues(BigDecimal.valueOf(3.00))
                .build()

        parkingSessionRepository.saveAll(Arrays.asList(testParkingSession1, testParkingSession2, testParkingSession3, testParkingSession4))
    }

    def cleanup() {
        parkingSessionRepository.deleteAll()
    }

    def "should return earnings for chosen day"() {
        given:
        String testDate = "2018-11-10"

        when:
        def result = mockMvc.perform(get('/earnings/check')
                .param("date", testDate))

        then:
        result.andExpect(status().isOk())

        and:
        result.andExpect(jsonPath('$.earnings').value(9.00))
    }

    def "should return 0 if there are no earnings on chosen day"() {
        given:
        String testDate = "2018-11-14"

        when:
        def result = mockMvc.perform(get('/earnings/check')
                .param("date", testDate))

        then:
        result.andExpect(status().isOk())

        and:
        result.andExpect(jsonPath('$.earnings').value(0.00))
    }
}
