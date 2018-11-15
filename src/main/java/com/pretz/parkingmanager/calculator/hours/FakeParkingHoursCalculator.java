package com.pretz.parkingmanager.calculator.hours;

import com.pretz.parkingmanager.domain.ParkingSession;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@Profile("test")
public class FakeParkingHoursCalculator implements ParkingHoursCalculator {
    @Override
    public double calculateParkingHours(ParkingSession parkingSession) {

        Timestamp parkingStartTime = Timestamp.from(Instant.now()
                .minus(2, ChronoUnit.HOURS)
                .minus(45, ChronoUnit.MINUTES)
                .minus(11, ChronoUnit.SECONDS));

        Timestamp presentTime = Timestamp.from(Instant.now());

        return (presentTime.getTime() - parkingStartTime.getTime()) / (1000.0 * 60.0 * 60.0);
    }
}
