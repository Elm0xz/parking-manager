package com.pretz.parkingmanager.calculator.hours;

import com.pretz.parkingmanager.domain.ParkingSession;

public interface ParkingHoursCalculator {

    static final double MILLIS_IN_HOUR = 1000.0 * 60.0 * 60.0;

    long calculateParkingHours(ParkingSession parkingSession);
}
