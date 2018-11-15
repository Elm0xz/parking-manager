package com.pretz.parkingmanager.calculator.hours;

import com.pretz.parkingmanager.domain.ParkingSession;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class DefaultParkingHoursCalculator implements ParkingHoursCalculator {

    @Override
    public double calculateParkingHours(ParkingSession parkingSession) {

        Timestamp parkingStartTime = parkingSession.getStartTime();
        Timestamp presentTime = Timestamp.from(Instant.now());

        return (presentTime.getTime() - parkingStartTime.getTime()) / (1000.0 * 60.0 * 60.0);
    }
}
