package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.calculator.currency.CurrencyConverter;
import com.pretz.parkingmanager.calculator.DuesCalculator;
import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.DuesRequestDTO;
import com.pretz.parkingmanager.dto.DuesResponseDTO;
import com.pretz.parkingmanager.exception.ParkingSessionNotActiveException;
import com.pretz.parkingmanager.exception.UnknownCurrencyException;
import com.pretz.parkingmanager.repository.ParkingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DuesCheckService {

    private final ParkingSessionRepository parkingSessionRepository;
    private final DuesCalculator duesCalculator;
    private final Map<String, CurrencyConverter> currencyConverters;

    public DuesResponseDTO checkDues(DuesRequestDTO duesRequestDTO) {

        String vehicleId = duesRequestDTO.getVehicleId();
        long parkingSessionId = duesRequestDTO.getParkingSessionId();

        CurrencyConverter currencyConverter = getCurrencyConverter(duesRequestDTO.getCurrencyCode());

        ParkingSession parkingSession = parkingSessionRepository.findByVehicleIdAndId(vehicleId, parkingSessionId)
                .orElseThrow(ParkingSessionNotActiveException::new);

        BigDecimal dues = duesCalculator.calculateDues(parkingSession, currencyConverter);

        return DuesResponseDTO.builder()
                .parkingStartTime(parkingSession.getStartTime())
                .dues(dues)
                .build();
    }

    private CurrencyConverter getCurrencyConverter(String currencyCode) {

        String currencyKey = currencyCode + "CurrencyConverter";
        return Optional.ofNullable(currencyConverters.get(currencyKey)).orElseThrow(UnknownCurrencyException::new);

    }
}
