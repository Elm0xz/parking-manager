package com.pretz.parkingmanager.calculator.currency;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PLNCurrencyConverter implements CurrencyConverter {

    @Override
    public BigDecimal getCurrencyMultiplier() {
        return BigDecimal.ONE;
    }
}
