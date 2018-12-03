package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.DuesRequestDTO;
import com.pretz.parkingmanager.dto.DuesResponseDTO;
import com.pretz.parkingmanager.exception.InvalidParkingSessionException;
import com.pretz.parkingmanager.repository.ParkingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DuesCheckService {

    private final ParkingSessionRepository parkingSessionRepository;
    private final DuesCalculationService duesCalculationService;

    public DuesResponseDTO checkDues(DuesRequestDTO duesRequestDTO) {

        String vehicleId = duesRequestDTO.getVehicleId();
        long parkingSessionId = duesRequestDTO.getParkingSessionId();

        ParkingSession parkingSession = parkingSessionRepository.findByVehicleIdAndIdAndStopTimeIsNull(vehicleId, parkingSessionId)
                .orElseThrow(InvalidParkingSessionException::new);

        BigDecimal dues = duesCalculationService.calculateDues(parkingSession, duesRequestDTO.getCurrencyCode());

        return DuesResponseDTO.builder()
                .parkingStartTime(parkingSession.getStartTime())
                .dues(dues)
                .build();
    }

    public DuesResponseDTO checkDues(long id, String currencyCode) {

        ParkingSession parkingSession = parkingSessionRepository.findByIdAndStopTimeIsNotNull(id)
                .orElseThrow(InvalidParkingSessionException::new);

        BigDecimal dues = duesCalculationService.calculateDues(parkingSession, currencyCode);

        return DuesResponseDTO.builder()
                .parkingStartTime(parkingSession.getStartTime())
                .dues(dues)
                .build();
    }
}
