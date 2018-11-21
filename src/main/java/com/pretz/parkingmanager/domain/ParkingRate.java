package com.pretz.parkingmanager.domain;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

//TODO maybe create strategies for these two rates when implementing due calculation logic
@AllArgsConstructor
public enum ParkingRate {
    REGULAR(BigDecimal.valueOf(1.0),BigDecimal.valueOf(2.0), BigDecimal.valueOf(1.5)),
    DISABLED(BigDecimal.valueOf(0.0),BigDecimal.valueOf(2.0), BigDecimal.valueOf(1.2));

    private BigDecimal firstHourFee;
    private BigDecimal secondHourFee;
    private BigDecimal furtherHoursMultiplier;
}
