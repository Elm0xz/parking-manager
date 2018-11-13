package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.DuesRequestDTO;
import com.pretz.parkingmanager.dto.DuesResponseDTO;
import com.pretz.parkingmanager.exception.ParkingSessionNotActiveException;
import com.pretz.parkingmanager.repository.ParkingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
@RequiredArgsConstructor
public class DuesCheckService {

    private final ParkingSessionRepository parkingSessionRepository;
    private final DuesCalculator duesCalculator;

    public DuesResponseDTO checkDues(DuesRequestDTO duesRequestDTO) {

        String vehicleId = duesRequestDTO.getVehicleId();
        long parkingSessionId = duesRequestDTO.getParkingSessionId();
        Currency currency = Currency.getInstance(duesRequestDTO.getCurrencyCode());

        ParkingSession parkingSession = parkingSessionRepository.findByVehicleIdAndId(vehicleId, parkingSessionId)
                .orElseThrow(ParkingSessionNotActiveException::new);

        BigDecimal dues = duesCalculator.calculateDues(parkingSession, currency);

        return DuesResponseDTO.builder()
                .parkingStartTime(parkingSession.getStartTime())
                .dues(dues)
                .build();
    }
}
