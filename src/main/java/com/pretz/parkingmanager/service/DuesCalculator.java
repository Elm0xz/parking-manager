package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.domain.ParkingRate;
import com.pretz.parkingmanager.domain.ParkingSession;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class DuesCalculator {

    public BigDecimal calculateDues(ParkingSession parkingSession, CurrencyConverter currencyConverter) {

        ParkingRate parkingRate = parkingSession.getParkingRate();

        double parkingHours = calculateParkingHours(parkingSession);

        BigDecimal dues;

        if (parkingHours <= 1) {
            dues = calculateDuesForOneHourParking(parkingRate);
        } else if (parkingHours > 1 && parkingHours <= 2) {
            dues = calculateDuesForTwoHoursParking(parkingRate);
        } else {
            dues = calculateDuesForParkingLongerThanTwoHours(parkingRate, parkingHours);
        }

        BigDecimal currencyMultiplier = currencyConverter.getCurrencyMultiplier();
        return dues.multiply(currencyMultiplier).setScale(2, RoundingMode.HALF_UP);
    }

    private double calculateParkingHours(ParkingSession parkingSession) {

        Timestamp parkingStartTime = parkingSession.getStartTime();

        Timestamp presentTime = Timestamp.from(Instant.now());

        return (presentTime.getTime() - parkingStartTime.getTime()) / (1000.0 * 60.0 * 60.0);
    }

    private BigDecimal calculateDuesForOneHourParking(ParkingRate parkingRate) {
        return parkingRate.getFirstHourFee();
    }

    private BigDecimal calculateDuesForTwoHoursParking(ParkingRate parkingRate) {
        return parkingRate.getFirstHourFee().add(parkingRate.getSecondHourFee());
    }

    private BigDecimal calculateDuesForParkingLongerThanTwoHours(ParkingRate parkingRate, double parkingHours) {

        BigDecimal dues = calculateDuesForTwoHoursParking(parkingRate);
        BigDecimal nextHourFee = parkingRate.getSecondHourFee();
        for (int hour = 2; hour < parkingHours; hour++) {
            nextHourFee = nextHourFee.multiply(parkingRate.getFurtherHoursMultiplier());
            dues = dues.add(nextHourFee);
        }
        return dues;
    }
}
