package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.dto.ParkingStartRequestDTO;
import com.pretz.parkingmanager.exception.ParkingSessionAlreadyActiveException;
import com.pretz.parkingmanager.service.ParkingMeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/driver/")
@RequiredArgsConstructor
public class ParkingMeterController {

    private final ParkingMeterService parkingMeterService;

    @PostMapping("start-parking")
    public ResponseEntity<Void> startParkingMeter(@RequestBody @Valid ParkingStartRequestDTO parkingStartRequestDTO) throws ParkingSessionAlreadyActiveException {

        parkingMeterService.startParkingMeter(parkingStartRequestDTO);

        return ResponseEntity.ok().build();
    }
}
