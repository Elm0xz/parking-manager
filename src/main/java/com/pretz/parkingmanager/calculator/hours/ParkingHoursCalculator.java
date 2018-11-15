package com.pretz.parkingmanager.calculator.hours;

import com.pretz.parkingmanager.domain.ParkingSession;

public interface ParkingHoursCalculator {

    double calculateParkingHours(ParkingSession parkingSession);
}
