package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.ParkingStartRequestDTO;
import com.pretz.parkingmanager.exception.ParkingSessionAlreadyActiveException;
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

    //TODO this could be changed with this exception handler from spring
    public void startParkingMeter(ParkingStartRequestDTO parkingStartRequestDTO) {

        String vehicleId = parkingStartRequestDTO.getVehicleId();

        if (isParkingSessionAlreadyActive(vehicleId)) {
            throw new ParkingSessionAlreadyActiveException();
        }
        else {
            ParkingSession parkingSession = parkingSessionMapper.fromParkingStartRequestDTO(parkingStartRequestDTO);
            parkingSession.setStartTime(Timestamp.from(Instant.now()));
            parkingSessionRepository.save(parkingSession);
        }
    }

    private boolean isParkingSessionAlreadyActive(String vehicleId) {
        return parkingSessionRepository.findByVehicleIdAndStopTimeIsNull(vehicleId).isPresent();
    }
}
