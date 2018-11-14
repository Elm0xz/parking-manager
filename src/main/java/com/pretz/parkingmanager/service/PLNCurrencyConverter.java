package com.pretz.parkingmanager.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PLNCurrencyConverter implements CurrencyConverter {

    @Override
    public BigDecimal getCurrencyMultiplier() {
        return BigDecimal.ONE;
    }
}
