package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.ParkingMeterResponseDTO;
import com.pretz.parkingmanager.dto.ParkingStartDTO;
import com.pretz.parkingmanager.dto.ParkingStopDTO;
import com.pretz.parkingmanager.exception.ParkingSessionAlreadyActiveException;
import com.pretz.parkingmanager.exception.ParkingSessionNotActiveException;
import com.pretz.parkingmanager.mapper.ParkingSessionMapper;
import com.pretz.parkingmanager.repository.ParkingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ParkingMeterService {

    private final ParkingSessionRepository parkingSessionRepository;
    private final ParkingSessionMapper parkingSessionMapper;
    private final DuesCalculationService duesCalculationService;

    public ParkingMeterResponseDTO startParkingMeter(final ParkingStartDTO parkingStartDTO) {

        String vehicleId = parkingStartDTO.getVehicleId();

        if (isParkingSessionAlreadyActive(vehicleId)) {
            throw new ParkingSessionAlreadyActiveException();
        } else {

            ParkingSession parkingSession = parkingSessionMapper.fromParkingStartDTO(parkingStartDTO);
            parkingSession.setStartTime(Timestamp.from(Instant.now()));
            parkingSession = parkingSessionRepository.save(parkingSession);

            return parkingSessionMapper.fromParkingSession(parkingSession);
        }
    }

    public ParkingMeterResponseDTO stopParkingMeter(final ParkingStopDTO parkingStopDTO) {

        String vehicleId = parkingStopDTO.getVehicleId();
        long parkingSessionId = parkingStopDTO.getParkingSessionId();

        if (isParkingSessionAlreadyActive(vehicleId, parkingSessionId)) {
            ParkingSession parkingSessionToBeStopped = parkingSessionRepository.findByVehicleIdAndIdAndStopTimeIsNull(vehicleId, parkingSessionId).get();
            parkingSessionToBeStopped.setStopTime(Timestamp.from(Instant.now()));
            parkingSessionToBeStopped.setDues(duesCalculationService.calculateDues(parkingSessionToBeStopped, parkingStopDTO.getCurrencyCode()));
            parkingSessionToBeStopped = parkingSessionRepository.save(parkingSessionToBeStopped);

            return parkingSessionMapper.fromParkingSession(parkingSessionToBeStopped);
        } else {
            throw new ParkingSessionNotActiveException();
        }
    }

    private boolean isParkingSessionAlreadyActive(final String vehicleId) {
        return parkingSessionRepository.findByVehicleIdAndStopTimeIsNull(vehicleId).isPresent();
    }

    private boolean isParkingSessionAlreadyActive(final String vehicleId, final long parkingSessionId) {
        return parkingSessionRepository.findByVehicleIdAndIdAndStopTimeIsNull(vehicleId, parkingSessionId).isPresent();
    }
}
