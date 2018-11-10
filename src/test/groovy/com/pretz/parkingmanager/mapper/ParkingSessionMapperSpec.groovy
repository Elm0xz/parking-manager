package com.pretz.parkingmanager.mapper

import com.pretz.parkingmanager.UnitTest
import com.pretz.parkingmanager.domain.ParkingRate
import com.pretz.parkingmanager.domain.ParkingSession
import com.pretz.parkingmanager.dto.ParkingMeterResponseDTO
import com.pretz.parkingmanager.dto.ParkingStartDTO
import org.junit.experimental.categories.Category
import org.modelmapper.ModelMapper
import spock.lang.Specification
import spock.lang.Unroll

import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit

import static net.java.quickcheck.generator.CombinedGeneratorsIterables.somePairs
import static net.java.quickcheck.generator.PrimitiveGenerators.longs
import static net.java.quickcheck.generator.PrimitiveGenerators.strings

@Category(UnitTest.class)
class ParkingSessionMapperSpec extends Specification {

    ParkingSessionMapper parkingSessionMapper
    ParkingStartDTO.ParkingStartDTOBuilder parkingStartDTOBuilder
    ParkingSession.ParkingSessionBuilder parkingSessionBuilder

    def setup() {

        ModelMapper testModelMapper = new ModelMapper()
        testModelMapper.addConverter(new ParkingStartDTOToParkingSessionConverter())
        testModelMapper.addConverter(new ParkingSessionToParkingMeterResponseDTOConverter())
        parkingSessionMapper = new ParkingSessionMapper(testModelMapper)

        parkingStartDTOBuilder = ParkingStartDTO.builder()
                .vehicleId("ABC1234")
                .parkingRateId(1)

        parkingSessionBuilder = ParkingSession.builder()
                .vehicleId("EWE4567")
                .id(1)
    }

    @Unroll
    def "mapper should map ParkingStartDTO to ParkingSession object with vehicleId and parkingRate fields initalized and other fields null"() {

        given:
        ParkingStartDTO testParkingStartDTO = parkingStartDTOBuilder
                .vehicleId(generatedValues.first)
                .parkingRateId(generatedValues.second)
                .build()

        when:
        ParkingSession testParkingSession = parkingSessionMapper.fromParkingStartDTO(testParkingStartDTO)

        then:
        testParkingSession.getVehicleId() == testParkingStartDTO.getVehicleId()
        testParkingSession.getParkingRate() != null
        testParkingSession.getId() == 0
        testParkingSession.getStartTime() == null
        testParkingSession.getStopTime() == null

        where:
        generatedValues << somePairs(strings(), longs(0, 100))
    }

    def "mapper should map ParkingStartDTO to ParkingSession object with REGULAR parking rate"() {

        given:
        ParkingStartDTO testParkingStartDTO = parkingStartDTOBuilder
                .parkingRateId(1)
                .build()

        when:
        ParkingSession testParkingSession = parkingSessionMapper.fromParkingStartDTO(testParkingStartDTO)

        then:
        testParkingSession.getParkingRate() == ParkingRate.REGULAR
    }

    def "mapper should map ParkingStartDTO to ParkingSession object with DISABLED parking rate"() {

        given:
        ParkingStartDTO testParkingStartDTO = parkingStartDTOBuilder
                .parkingRateId(2)
                .build()

        when:
        ParkingSession testParkingSession = parkingSessionMapper.fromParkingStartDTO(testParkingStartDTO)

        then:
        testParkingSession.getParkingRate() == ParkingRate.DISABLED
    }

    def "mapper should map ParkingStartDTO to ParkingSession object with NONE parking rate"() {

        given:
        ParkingStartDTO testParkingStartDTO = parkingStartDTOBuilder
                .parkingRateId(0)
                .build()

        when:
        ParkingSession testParkingSession = parkingSessionMapper.fromParkingStartDTO(testParkingStartDTO)

        then:
        testParkingSession.getParkingRate() == ParkingRate.NONE
    }

    def "mapper should map ParkingSession to ParkingMeterResponseDTO with timestamp calculated from start time if stop time is null"() {

        given:
        Timestamp startTimestamp = Timestamp.from(Instant.now())

        ParkingSession testParkingSession = parkingSessionBuilder
                .startTime(startTimestamp)
                .stopTime(null)
                .build()

        when:
        ParkingMeterResponseDTO testParkingMeterResponseDTO = parkingSessionMapper.fromParkingSession(testParkingSession)

        then:
        testParkingMeterResponseDTO.vehicleId == testParkingSession.vehicleId
        testParkingMeterResponseDTO.parkingSessionId == testParkingSession.id
        testParkingMeterResponseDTO.timestamp == startTimestamp
    }

    def "mapper should map ParkingSession to ParkingMeterResponseDTO with timestamp calculated from stop time if stop time is not null"() {

        given:
        Timestamp startTimestamp = Timestamp.from(Instant.now().minus(5, ChronoUnit.HOURS))
        Timestamp stopTimestamp = Timestamp.from(Instant.now().minus(1, ChronoUnit.HOURS))

        ParkingSession testParkingSession = parkingSessionBuilder
                .startTime(startTimestamp)
                .stopTime(stopTimestamp)
                .build()

        when:
        ParkingMeterResponseDTO testParkingMeterResponseDTO = parkingSessionMapper.fromParkingSession(testParkingSession)

        then:
        testParkingMeterResponseDTO.vehicleId == testParkingSession.vehicleId
        testParkingMeterResponseDTO.parkingSessionId == testParkingSession.id
        testParkingMeterResponseDTO.timestamp == stopTimestamp
    }
}
