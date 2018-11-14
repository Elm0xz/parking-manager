package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.exception.UnknownCurrencyException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GenericCurrencyConverter implements CurrencyConverter {
    @Override
    public BigDecimal getCurrencyMultiplier() {
        throw new UnknownCurrencyException();
    }
}
