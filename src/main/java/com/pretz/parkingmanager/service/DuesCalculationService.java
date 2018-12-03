package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.calculator.DuesCalculator;
import com.pretz.parkingmanager.calculator.currency.CurrencyConverter;
import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.exception.UnknownCurrencyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DuesCalculationService {

    private final DuesCalculator duesCalculator;
    private final Map<String, CurrencyConverter> currencyConverters;

    BigDecimal calculateDues(ParkingSession parkingSession, String currencyCode) {

        CurrencyConverter currencyConverter = getCurrencyConverter(currencyCode);

        return duesCalculator.calculateDues(parkingSession, currencyConverter);
    }

    private CurrencyConverter getCurrencyConverter(String currencyCode) {

        String currencyKey = currencyCode + CurrencyConverter.class.getSimpleName();
        return Optional.ofNullable(currencyConverters.get(currencyKey)).orElseThrow(UnknownCurrencyException::new);
    }
}
