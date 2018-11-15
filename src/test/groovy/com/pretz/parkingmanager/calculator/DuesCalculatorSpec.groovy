package com.pretz.parkingmanager.calculator

import com.pretz.parkingmanager.UnitTest
import com.pretz.parkingmanager.calculator.currency.CurrencyConverter
import com.pretz.parkingmanager.calculator.DuesCalculator
import com.pretz.parkingmanager.calculator.hours.DefaultParkingHoursCalculator
import com.pretz.parkingmanager.domain.ParkingRate
import com.pretz.parkingmanager.domain.ParkingSession
import org.junit.experimental.categories.Category
import spock.lang.Specification

import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit

@Category(UnitTest.class)
class DuesCalculatorSpec extends Specification {

    private DuesCalculator duesCalculator
    private CurrencyConverter currencyConverter

    private ParkingSession.ParkingSessionBuilder testBuilder

    def setup() {
        currencyConverter = Stub(CurrencyConverter)
        currencyConverter.getCurrencyMultiplier() >> BigDecimal.ONE

        duesCalculator = new DuesCalculator(new DefaultParkingHoursCalculator())

        testBuilder = ParkingSession.builder()
    }

    def "should calculate 1 PLN due for 'regular' parking stop shorter than 1 hour"() {

        given:
        ParkingSession testParkingSession = testBuilder
                .startTime(Timestamp.from(Instant.now()
                .minus(45, ChronoUnit.MINUTES)
                .minus(11, ChronoUnit.SECONDS)))
                .parkingRate(ParkingRate.REGULAR)
                .build()

        when:
        BigDecimal testDues = duesCalculator.calculateDues(testParkingSession, currencyConverter)

        then:
        testDues == BigDecimal.valueOf(1.00)
    }

    def "should calculate 3 PLN due for 'regular' parking stop between 1 and 2 hours"() {

        given:
        ParkingSession testParkingSession = testBuilder
                .startTime(Timestamp.from(Instant.now()
                .minus(1, ChronoUnit.HOURS)
                .minus(41, ChronoUnit.MINUTES)
                .minus(53, ChronoUnit.SECONDS)))
                .parkingRate(ParkingRate.REGULAR)
                .build()

        when:
        BigDecimal testDues = duesCalculator.calculateDues(testParkingSession, currencyConverter)

        then:
        testDues == BigDecimal.valueOf(3.00)
    }

    def "should calculate 6 PLN due for 'regular' parking stop between 2 and 3 hours"() {

        given:
        ParkingSession testParkingSession = testBuilder
                .startTime(Timestamp.from(Instant.now()
                .minus(2, ChronoUnit.HOURS)
                .minus(33, ChronoUnit.MINUTES)
                .minus(47, ChronoUnit.SECONDS)))
                .parkingRate(ParkingRate.REGULAR)
                .build()

        when:
        BigDecimal testDues = duesCalculator.calculateDues(testParkingSession, currencyConverter)

        then:
        testDues == BigDecimal.valueOf(6.00)
    }

    def "should calculate 10.5 PLN due for 'regular' parking stop between 3 and 4 hours"() {

        given:
        ParkingSession testParkingSession = testBuilder
                .startTime(Timestamp.from(Instant.now()
                .minus(3, ChronoUnit.HOURS)
                .minus(12, ChronoUnit.MINUTES)
                .minus(36, ChronoUnit.SECONDS)))
                .parkingRate(ParkingRate.REGULAR)
                .build()

        when:
        BigDecimal testDues = duesCalculator.calculateDues(testParkingSession, currencyConverter)

        then:
        testDues == BigDecimal.valueOf(10.50)
    }

    def "should calculate 44887,97 PLN due for 'regular' parking stop between 23 and 24 hours"() {

        given:
        ParkingSession testParkingSession = testBuilder
                .startTime(Timestamp.from(Instant.now()
                .minus(23, ChronoUnit.HOURS)
                .minus(57, ChronoUnit.MINUTES)
                .minus(53, ChronoUnit.SECONDS)))
                .parkingRate(ParkingRate.REGULAR)
                .build()

        when:
        BigDecimal testDues = duesCalculator.calculateDues(testParkingSession, currencyConverter)

        then:
        testDues == BigDecimal.valueOf(44887.97)
    }

    def "should calculate 0 PLN due for 'disabled' parking stop shorter than 1 hour"() {

        given:
        ParkingSession testParkingSession = testBuilder
                .startTime(Timestamp.from(Instant.now()
                .minus(17, ChronoUnit.MINUTES)
                .minus(22, ChronoUnit.SECONDS)))
                .parkingRate(ParkingRate.DISABLED)
                .build()

        when:
        BigDecimal testDues = duesCalculator.calculateDues(testParkingSession, currencyConverter)

        then:
        testDues == BigDecimal.ZERO
    }

    def "should calculate 2 PLN due for 'disabled' parking stop between 1 and 2 hours"() {

        given:
        ParkingSession testParkingSession = testBuilder
                .startTime(Timestamp.from(Instant.now()
                .minus(1, ChronoUnit.HOURS)
                .minus(25, ChronoUnit.MINUTES)
                .minus(42, ChronoUnit.SECONDS)))
                .parkingRate(ParkingRate.DISABLED)
                .build()

        when:
        BigDecimal testDues = duesCalculator.calculateDues(testParkingSession, currencyConverter)

        then:
        testDues == BigDecimal.valueOf(2.00)
    }

    def "should calculate 4.4 PLN due for 'disabled' parking stop between 2 and 3 hours"() {

        given:
        ParkingSession testParkingSession = testBuilder
                .startTime(Timestamp.from(Instant.now()
                .minus(2, ChronoUnit.HOURS)
                .minus(37, ChronoUnit.MINUTES)
                .minus(15, ChronoUnit.SECONDS)))
                .parkingRate(ParkingRate.DISABLED)
                .build()

        when:
        BigDecimal testDues = duesCalculator.calculateDues(testParkingSession, currencyConverter)

        then:
        testDues == BigDecimal.valueOf(4.40)

    }

    def "should calculate 7.28 PLN due for 'disabled' parking stop between 3 and 4 hours"() {

        given:
        ParkingSession testParkingSession = testBuilder
                .startTime(Timestamp.from(Instant.now()
                .minus(3, ChronoUnit.HOURS)
                .minus(51, ChronoUnit.MINUTES)
                .minus(3, ChronoUnit.SECONDS)))
                .parkingRate(ParkingRate.DISABLED)
                .build()

        when:
        BigDecimal testDues = duesCalculator.calculateDues(testParkingSession, currencyConverter)

        then:
        testDues == BigDecimal.valueOf(7.28)

    }

    def "should calculate 652,47 PLN due for 'disabled' parking stop between 23 and 24 hours"() {

        given:
        ParkingSession testParkingSession = testBuilder
                .startTime(Timestamp.from(Instant.now()
                .minus(23, ChronoUnit.HOURS)
                .minus(4, ChronoUnit.MINUTES)
                .minus(39, ChronoUnit.SECONDS)))
                .parkingRate(ParkingRate.DISABLED)
                .build()

        when:
        BigDecimal testDues = duesCalculator.calculateDues(testParkingSession, currencyConverter)

        then:
        testDues == BigDecimal.valueOf(652.47)
    }

    def "should calculate no dues for 'none' parking stop"() {

        given:
        ParkingSession testParkingSession = testBuilder
                .startTime(Timestamp.from(Instant.now()
                .minus(23, ChronoUnit.HOURS)
                .minus(4, ChronoUnit.MINUTES)
                .minus(39, ChronoUnit.SECONDS)))
                .parkingRate(ParkingRate.NONE)
                .build()

        when:
        BigDecimal testDues = duesCalculator.calculateDues(testParkingSession, currencyConverter)

        then:
        testDues == BigDecimal.ZERO
    }
}
