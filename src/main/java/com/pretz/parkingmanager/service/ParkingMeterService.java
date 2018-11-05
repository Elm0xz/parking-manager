package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.ParkingMeterResponseDTO;
import com.pretz.parkingmanager.dto.ParkingStartDTO;
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

    public ParkingMeterResponseDTO startParkingMeter(final ParkingStartDTO parkingStartDTO) {

        String vehicleId = parkingStartDTO.getVehicleId();

        if (isParkingSessionAlreadyActive(vehicleId)) {
            throw new ParkingSessionAlreadyActiveException();
        } else {

            ParkingSession parkingSession = parkingSessionMapper.fromParkingStartDTO(parkingStartDTO);
            parkingSession.setStartTime(Timestamp.from(Instant.now()));
            parkingSession = parkingSessionRepository.save(parkingSession);

            return ParkingMeterResponseDTO.builder()
                    .vehicleId(parkingSession.getVehicleId())
                    .parkingSessionId(parkingSession.getId())
                    .timestamp(parkingSession.getStartTime())
                    .build();
        }
    }

    private boolean isParkingSessionAlreadyActive(final String vehicleId) {
        return parkingSessionRepository.findByVehicleIdAndStopTimeIsNull(vehicleId).isPresent();
    }
}
