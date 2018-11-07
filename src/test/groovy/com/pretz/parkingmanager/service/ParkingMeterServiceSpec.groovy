package com.pretz.parkingmanager.service

import com.pretz.parkingmanager.UnitTest
import com.pretz.parkingmanager.domain.ParkingRate
import com.pretz.parkingmanager.domain.ParkingSession
import com.pretz.parkingmanager.dto.ParkingMeterResponseDTO
import com.pretz.parkingmanager.dto.ParkingStartDTO
import com.pretz.parkingmanager.exception.ParkingSessionAlreadyActiveException
import com.pretz.parkingmanager.mapper.ParkingSessionMapper
import com.pretz.parkingmanager.mapper.ParkingSessionToParkingMeterResponseDTOConverter
import com.pretz.parkingmanager.mapper.ParkingStartDTOToParkingSessionConverter
import com.pretz.parkingmanager.repository.ParkingSessionRepository
import org.junit.Assert
import org.junit.experimental.categories.Category
import org.modelmapper.ModelMapper
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit

@Category(UnitTest.class)
class ParkingMeterServiceSpec extends Specification {

    private ParkingSessionRepository parkingSessionRepository
    private ParkingSessionMapper parkingSessionMapper

    private ParkingMeterService parkingMeterService

    def setup() {
        parkingSessionRepository = Mock(ParkingSessionRepository.class)

        ModelMapper modelMapper = new ModelMapper()
        modelMapper.addConverter(new ParkingStartDTOToParkingSessionConverter())
        modelMapper.addConverter(new ParkingSessionToParkingMeterResponseDTOConverter())

        parkingSessionMapper = new ParkingSessionMapper(modelMapper)

        parkingMeterService = new ParkingMeterService(parkingSessionRepository, parkingSessionMapper)
    }

    def "should start parking session for vehicle that doesn't have an active parking session"() {

        given:
        String testVehicleId = "AWC1342"
        long testParkingRateId = 1

        ParkingStartDTO testParkingStartDTO = ParkingStartDTO.builder()
                .vehicleId(testVehicleId)
                .parkingRateId(testParkingRateId)
                .build()

        ParkingSession testParkingSession = ParkingSession.builder()
                .vehicleId(testVehicleId)
                .parkingRate(ParkingRate.REGULAR)
                .startTime(Timestamp.from(Instant.now()))
                .build()

        //parkingSessionRepository.save(_ as ParkingSession) >> testParkingSession
        parkingSessionRepository.findByVehicleIdAndStopTimeIsNull(_ as String) >> Optional.empty()

        when:
        ParkingMeterResponseDTO parkingMeterResponseDTO = parkingMeterService.startParkingMeter(testParkingStartDTO)

        then:
        1 * parkingSessionRepository.save(_ as ParkingSession) >> testParkingSession
        parkingMeterResponseDTO.vehicleId == testVehicleId
        Assert.assertNotNull(parkingMeterResponseDTO.parkingSessionId)
        Assert.assertNotNull(parkingMeterResponseDTO.timestamp)
    }

    def "should not start parking session for vehicle that is already parked"() {

        given:
        String testVehicleId = "XBM5543"

        ParkingStartDTO testParkingStartDTO = ParkingStartDTO.builder()
                .vehicleId(testVehicleId)
                .parkingRateId(1)
                .build()

        parkingSessionRepository.findByVehicleIdAndStopTimeIsNull(_) >> Optional.of(ParkingSession.builder()
                .vehicleId(testVehicleId)
                .startTime(Timestamp.from(Instant.now().minus(3, ChronoUnit.HOURS)))
                .stopTime(null)
                .id(1)
                .parkingRate(ParkingRate.REGULAR)
                .build())

        when:
        ParkingMeterResponseDTO parkingMeterResponseDTO = parkingMeterService.startParkingMeter(testParkingStartDTO)

        then:
        thrown ParkingSessionAlreadyActiveException
    }
}
