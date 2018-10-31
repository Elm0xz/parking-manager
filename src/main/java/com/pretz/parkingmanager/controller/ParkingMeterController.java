package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.dto.ParkingStartDTO;
import com.pretz.parkingmanager.service.ParkingMeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/driver/")
@RequiredArgsConstructor
public class ParkingMeterController {

    private final ParkingMeterService parkingMeterService;

    @PostMapping("start-parking")
    public ResponseEntity<Void> startParkingMeter(@RequestBody @Valid ParkingStartDTO parkingStartDTO) {

        parkingMeterService.startParkingMeter(parkingStartDTO);

        return ResponseEntity.ok().build();
    }
}
