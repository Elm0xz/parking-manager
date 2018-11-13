package com.pretz.parkingmanager.api

import com.pretz.parkingmanager.IntegrationTest
import org.junit.experimental.categories.Category
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Category(IntegrationTest.class)
class DuesCheckMvcSpec extends Specification {

    def "Should show dues for specified vehicle and parking session in requested currency when vehicle is still parked"() {

    }

    def "Should not show dues for specified vehicle and parking session in requested currency if vehicle is not parked and return code 409 (conflict)"() {

    }

    def "Should show dues for specified vehicle and parking session in requested currency when vehicle has just stopped parking"() {

    }

    def "Should not show dues for specified vehicle and parking session in requested currency when currency code is not handled"() {

    }

}
