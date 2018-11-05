package com.pretz.parkingmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ParkingSessionExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Vehicle with this id is already parked")
    @ExceptionHandler(value = ParkingSessionAlreadyActiveException.class)
    protected void handleParkingSessionAlreadyActive(ParkingSessionAlreadyActiveException ex) {
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Vehicle with this id is already parked")
    @ExceptionHandler(value = ParkingSessionAlreadyActiveException.class)
    protected void handleInvalidParkingRateException(InvalidParkingRateException ex) {

    }
}
