package com.pretz.parkingmanager.dto

import com.pretz.parkingmanager.UnitTest
import org.junit.Assert
import org.junit.experimental.categories.Category
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

@Category(UnitTest.class)
class ParkingStartDTOValidationSpec extends Specification {

    Validator validator
    ParkingStartDTO.ParkingStartDTOBuilder testBuilder

    def setup() {

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()
        validator = validatorFactory.getValidator()
        testBuilder = ParkingStartDTO.builder()
                .vehicleId("ABC1234")
                .parkingRateId(1)
    }

    def "validation should pass when vehicle id is consistent with basic vehicle id regex"() {

        given:
        ParkingStartDTO testParkingStartDTO = testBuilder
                .vehicleId("ERQ5234")
                .build()

        when:
        Set<ConstraintViolation<ParkingStartDTO>> violations = validator.validate(testParkingStartDTO)

        then:
        Assert.assertTrue(violations.isEmpty())
    }

    def "validation should pass when parking rate id has value 1 (regular rate)"() {

        given:
        ParkingStartDTO testParkingStartDTO = testBuilder
                .parkingRateId(1)
                .build()

        when:
        Set<ConstraintViolation<ParkingStartDTO>> violations = validator.validate(testParkingStartDTO)

        then:
        Assert.assertTrue(violations.isEmpty())
    }

    def "validation should pass when parking rate id has value 2 (disabled rate)"() {

        given:
        ParkingStartDTO testParkingStartDTO = testBuilder
                .parkingRateId(2)
                .build()

        when:
        Set<ConstraintViolation<ParkingStartDTO>> violations = validator.validate(testParkingStartDTO)

        then:
        Assert.assertTrue(violations.isEmpty())
    }

    def "validation should fail when vehicle id is null"() {

        given:
        ParkingStartDTO testParkingStartDTO = testBuilder
                .vehicleId(null)
                .build()

        when:
        Set<ConstraintViolation<ParkingStartDTO>> violations = validator.validate(testParkingStartDTO)

        then:
        Assert.assertEquals(1, violations.size())
        ConstraintViolation violation = ++violations.iterator()
        Assert.assertEquals("must not be null", violation.getMessage())
        Assert.assertEquals("vehicleId", violation.getPropertyPath().toString())
    }

    def "validation should fail when vehicle id is inconsistent with basic vehicle id regex"() {

        given:
        ParkingStartDTO testParkingStartDTO = testBuilder
                .vehicleId("XR%_#@GE")
                .build()

        when:
        Set<ConstraintViolation<ParkingStartDTO>> violations = validator.validate(testParkingStartDTO)

        then:
        Assert.assertEquals(1, violations.size())
        ConstraintViolation violation = ++violations.iterator()
        Assert.assertEquals("must match \"[A-Z]{3}[0-9]{4}\"", violation.getMessage())
        Assert.assertEquals("vehicleId", violation.getPropertyPath().toString())

    }

    def "validation should fail when parking rate id is 0"() {

        given:
        ParkingStartDTO testParkingStartDTO = testBuilder
                .parkingRateId(0)
                .build()

        when:
        Set<ConstraintViolation<ParkingStartDTO>> violations = validator.validate(testParkingStartDTO)

        then:
        Assert.assertEquals(1, violations.size())
        ConstraintViolation violation = ++violations.iterator()
        Assert.assertEquals("must be greater than or equal to 1", violation.getMessage())
        Assert.assertEquals("parkingRateId", violation.getPropertyPath().toString())
    }

    def "validation should fail when parking rate id is bigger than 2"() {

        given:
        ParkingStartDTO testParkingStartDTO = testBuilder
                .parkingRateId(5)
                .build()

        when:
        Set<ConstraintViolation<ParkingStartDTO>> violations = validator.validate(testParkingStartDTO)

        then:
        Assert.assertEquals(1, violations.size())
        ConstraintViolation violation = ++violations.iterator()
        Assert.assertEquals("must be less than or equal to 2", violation.getMessage())
        Assert.assertEquals("parkingRateId", violation.getPropertyPath().toString())
    }
}
