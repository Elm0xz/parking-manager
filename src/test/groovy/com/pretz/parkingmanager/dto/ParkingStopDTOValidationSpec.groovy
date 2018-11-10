package com.pretz.parkingmanager.dto

import org.junit.Assert
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class ParkingStopDTOValidationSpec extends Specification {

    Validator validator
    ParkingStopDTO.ParkingStopDTOBuilder testBuilder

    def setup() {

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()
        validator = validatorFactory.getValidator()
        testBuilder = ParkingStartDTO.builder()
                .vehicleId("SDE9999")
                .parkingSessionId(56)
    }

    def "validation should pass when vehicle id is consistent with basic vehicle id regex"() {
        given:
        ParkingStopDTO testParkingStopDTO = testBuilder
                .vehicleId("WQE7654")
                .build()

        when:
        Set<ConstraintViolation<ParkingStartDTO>> violations = validator.validate(testParkingStopDTO)

        then:
        Assert.assertTrue(violations.isEmpty())
    }

    def "validation should fail when vehicle id is null"() {
        given:
        ParkingStopDTO testParkingStopDTO = testBuilder
                .vehicleId(null)
                .build()

        when:
        Set<ConstraintViolation<ParkingStartDTO>> violations = validator.validate(testParkingStopDTO)

        then:
        Assert.assertEquals(1, violations.size())
        ConstraintViolation violation = ++violations.iterator()
        Assert.assertEquals("must not be null", violation.getMessage())
        Assert.assertEquals("vehicleId", violation.getPropertyPath().toString())
    }

    def "validation should fail when vehicle id is inconsistent with basic vehicle id regex"() {
        given:
        ParkingStopDTO testParkingStopDTO = testBuilder
                .vehicleId("BGRWRF33cgr")
                .build()

        when:
        Set<ConstraintViolation<ParkingStartDTO>> violations = validator.validate(testParkingStopDTO)

        then:
        Assert.assertEquals(1, violations.size())
        ConstraintViolation violation = ++violations.iterator()
        Assert.assertEquals("must match \"[A-Z]{3}[0-9]{4}\"", violation.getMessage())
        Assert.assertEquals("vehicleId", violation.getPropertyPath().toString())

    }

    def "validation should fail when parking session id is null"() {
        given:
        ParkingStopDTO testParkingStopDTO = testBuilder
                .parkingSessionId(null)
                .build()

        when:
        Set<ConstraintViolation<ParkingStartDTO>> violations = validator.validate(testParkingStopDTO)

        then:
        Assert.assertEquals(1, violations.size())
        ConstraintViolation violation = ++violations.iterator()
        Assert.assertEquals("must not be null", violation.getMessage())
        Assert.assertEquals("parkingSessionId", violation.getPropertyPath().toString())
    }
}

