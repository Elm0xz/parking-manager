package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.calculator.DuesCalculator;
import com.pretz.parkingmanager.calculator.currency.CurrencyConverter;
import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.DuesRequestDTO;
import com.pretz.parkingmanager.dto.DuesResponseDTO;
import com.pretz.parkingmanager.exception.InvalidParkingSessionException;
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

        ParkingSession parkingSession = parkingSessionRepository.findByVehicleIdAndIdAndStopTimeIsNull(vehicleId, parkingSessionId)
                .orElseThrow(InvalidParkingSessionException::new);

        BigDecimal dues = duesCalculator.calculateDues(parkingSession, currencyConverter);

        return DuesResponseDTO.builder()
                .parkingStartTime(parkingSession.getStartTime())
                .dues(dues)
                .build();
    }

    public DuesResponseDTO checkDues(long id) {

        ParkingSession parkingSession = parkingSessionRepository.findByIdAndStopTimeIsNotNull(id)
                .orElseThrow(InvalidParkingSessionException::new);

        //TODO should probably pass currency tag in stop parking meter request instead
        CurrencyConverter currencyConverter = getCurrencyConverter("PLN");

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
