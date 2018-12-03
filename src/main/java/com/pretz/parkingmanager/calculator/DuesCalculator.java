package com.pretz.parkingmanager.calculator;

import com.pretz.parkingmanager.calculator.currency.CurrencyConverter;
import com.pretz.parkingmanager.calculator.hours.ParkingHoursCalculator;
import com.pretz.parkingmanager.domain.ParkingRate;
import com.pretz.parkingmanager.domain.ParkingSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class DuesCalculator {

    private final ParkingHoursCalculator parkingHoursCalculator;

    public BigDecimal calculateDues(ParkingSession parkingSession, CurrencyConverter currencyConverter) {

        ParkingRate parkingRate = parkingSession.getParkingRate();
        double parkingHours = parkingHoursCalculator.calculateParkingHours(parkingSession);
        BigDecimal dues = calculateDuesBasingOnParkingHours(parkingRate, parkingHours);

        return convertToExpectedCurrency(dues, currencyConverter);
    }

    private BigDecimal calculateDuesBasingOnParkingHours(ParkingRate parkingRate, double parkingHours) {
        BigDecimal dues;
        if (parkingHours < 1) {
            dues = calculateDuesForOneHourParking(parkingRate);
        } else if (parkingHours >= 1 && parkingHours < 2) {
            dues = calculateDuesForTwoHoursParking(parkingRate);
        } else {
            dues = calculateDuesForParkingLongerThanTwoHours(parkingRate, parkingHours);
        }
        return dues;
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
        for (int hour = 2; hour <= parkingHours; hour++) {
            nextHourFee = nextHourFee.multiply(parkingRate.getFurtherHoursMultiplier());
            dues = dues.add(nextHourFee);
        }
        return dues;
    }

    private BigDecimal convertToExpectedCurrency(BigDecimal dues, CurrencyConverter currencyConverter) {
        return dues.multiply(currencyConverter.getCurrencyMultiplier()).setScale(2, RoundingMode.HALF_UP);
    }
}
