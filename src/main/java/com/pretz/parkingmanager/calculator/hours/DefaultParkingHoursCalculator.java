package com.pretz.parkingmanager.calculator.hours;

import com.pretz.parkingmanager.domain.ParkingSession;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class DefaultParkingHoursCalculator implements ParkingHoursCalculator {

    @Override
    public long calculateParkingHours(ParkingSession parkingSession) {

        Timestamp parkingStartTime = parkingSession.getStartTime();
        Timestamp presentTime = Timestamp.from(Instant.now());

        return (long) ((presentTime.getTime() - parkingStartTime.getTime()) / MILLIS_IN_HOUR);
    }
}
