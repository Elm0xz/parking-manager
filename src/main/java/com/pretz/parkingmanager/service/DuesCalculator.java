package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.domain.ParkingRate;
import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.exception.UnknownCurrencyException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Currency;

@Component
public class DuesCalculator {

    public BigDecimal calculateDues(ParkingSession parkingSession, Currency currency) {

        ParkingRate parkingRate = parkingSession.getParkingRate();

        Timestamp parkingStartTime = parkingSession.getStartTime();
        Timestamp presentTime = Timestamp.from(Instant.now());

        long timeDiff = (presentTime.getTime() - parkingStartTime.getTime())/(1000*60);

        BigDecimal currencyMultiplier;
        if (currency.getCurrencyCode().equals("PLN")) {
            currencyMultiplier = BigDecimal.ONE;
        } else {
            throw new UnknownCurrencyException();
        }

        if (timeDiff < 60) {
            return parkingRate.getFirstHourFee().multiply(currencyMultiplier).setScale(2, RoundingMode.HALF_UP);
        } else if (timeDiff > 60 && timeDiff < 120) {
            return parkingRate.getFirstHourFee().add(parkingRate.getSecondHourFee()).multiply(currencyMultiplier).setScale(2, RoundingMode.HALF_UP);
        } else {
            timeDiff -= 120;
            BigDecimal dues = parkingRate.getFirstHourFee().add(parkingRate.getSecondHourFee());
            BigDecimal nextHourFee = parkingRate.getSecondHourFee();
            while (timeDiff > 0) {
                nextHourFee = nextHourFee.multiply(parkingRate.getFurtherHoursMultiplier());
                dues = dues.add(nextHourFee);
                timeDiff -= 60;
            }
            return dues.multiply(currencyMultiplier).setScale(2, RoundingMode.HALF_UP);
        }
    }
}
