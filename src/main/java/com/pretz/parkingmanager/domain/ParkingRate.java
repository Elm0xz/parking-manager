package com.pretz.parkingmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public enum ParkingRate {
    NONE(BigDecimal.valueOf(0.0), BigDecimal.valueOf(0.0), BigDecimal.valueOf(0.0)),
    REGULAR(BigDecimal.valueOf(1.0), BigDecimal.valueOf(2.0), BigDecimal.valueOf(1.5)),
    DISABLED(BigDecimal.valueOf(0.0), BigDecimal.valueOf(2.0), BigDecimal.valueOf(1.2));

    private final BigDecimal firstHourFee;
    private final BigDecimal secondHourFee;
    private final BigDecimal furtherHoursMultiplier;
}
