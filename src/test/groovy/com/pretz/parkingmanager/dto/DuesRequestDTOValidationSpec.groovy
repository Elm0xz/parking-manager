package com.pretz.parkingmanager.dto

import com.pretz.parkingmanager.UnitTest
import org.junit.Assert
import org.junit.experimental.categories.Category
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

import static net.java.quickcheck.generator.PrimitiveGeneratorsIterables.someLongs

@Category(UnitTest.class)
class DuesRequestDTOValidationSpec extends Specification {

    Validator validator
    DuesRequestDTO.DuesRequestDTOBuilder testBuilder

    def setup() {

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()
        validator = validatorFactory.getValidator()
        testBuilder = DuesRequestDTO.builder()
                .vehicleId("ABC1234")
                .parkingSessionId(15)
                .currencyCode("PLN")
    }

    def "validation should pass when vehicle id is consistent with basic vehicle id regex"() {

        given:
        DuesRequestDTO testDuesRequestDTO = testBuilder
                .vehicleId("ABC1234")
                .build()

        when:
        Set<ConstraintViolation<DuesRequestDTO>> violations = validator.validate(testDuesRequestDTO)

        then:
        Assert.assertTrue(violations.isEmpty())
    }

    def "validation should fail when vehicle id is null"() {

        given:
        DuesRequestDTO testDuesRequestDTO = testBuilder
                .vehicleId(null)
                .build()

        when:
        Set<ConstraintViolation<DuesRequestDTO>> violations = validator.validate(testDuesRequestDTO)

        then:
        Assert.assertEquals(1, violations.size())
        ConstraintViolation violation = ++violations.iterator()
        Assert.assertEquals("must not be null", violation.getMessage())
        Assert.assertEquals("vehicleId", violation.getPropertyPath().toString())
    }

    def "validation should fail when vehicle id is inconsistent with basic vehicle id regex"() {

        given:
        DuesRequestDTO testDuesRequestDTO = testBuilder
                .vehicleId("XR%_#@GE")
                .build()

        when:
        Set<ConstraintViolation<DuesRequestDTO>> violations = validator.validate(testDuesRequestDTO)

        then:
        Assert.assertEquals(1, violations.size())
        ConstraintViolation violation = ++violations.iterator()
        Assert.assertEquals("must match \"[A-Z]{3}[0-9]{4}\"", violation.getMessage())
        Assert.assertEquals("vehicleId", violation.getPropertyPath().toString())
    }

    @Unroll
    def "validation should pass when parking session id is not 0"() {

        given:
        DuesRequestDTO testDuesRequestDTO = testBuilder
                .parkingSessionId(generatedValue)
                .build()

        when:
        Set<ConstraintViolation<DuesRequestDTO>> violations = validator.validate(testDuesRequestDTO)

        then:
        Assert.assertEquals(0, violations.size())

        where:
        generatedValue << someLongs(1, 100000)
    }

    def "validation should fail when parking session id is 0"() {

        given:
        DuesRequestDTO testDuesRequestDTO = testBuilder
                .parkingSessionId(0)
                .build()

        when:
        Set<ConstraintViolation<DuesRequestDTO>> violations = validator.validate(testDuesRequestDTO)

        then:
        Assert.assertEquals(1, violations.size())
        ConstraintViolation violation = ++violations.iterator()
        Assert.assertEquals("must be greater than or equal to 1", violation.getMessage())
        Assert.assertEquals("parkingSessionId", violation.getPropertyPath().toString())
    }

    def "validation should pass when currency code is consistent with currency code regex"() {

        given:
        DuesRequestDTO testDuesRequestDTO = testBuilder
                .currencyCode("USD")
                .build()

        when:
        Set<ConstraintViolation<DuesRequestDTO>> violations = validator.validate(testDuesRequestDTO)

        then:
        Assert.assertEquals(0, violations.size())
    }

    def "validation should fail when currency code is inconsistent with currency regex"() {

        given:
        DuesRequestDTO testDuesRequestDTO = testBuilder
                .currencyCode("X")
                .build()

        when:
        Set<ConstraintViolation<DuesRequestDTO>> violations = validator.validate(testDuesRequestDTO)

        then:
        Assert.assertEquals(1, violations.size())
        ConstraintViolation violation = ++violations.iterator()
        Assert.assertEquals("must match \"[A-Z]{3}\"", violation.getMessage())
        Assert.assertEquals("currencyCode", violation.getPropertyPath().toString())

    }
}