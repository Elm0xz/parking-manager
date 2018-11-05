package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.dto.ParkingMeterResponseDTO;
import com.pretz.parkingmanager.dto.ParkingStartDTO;
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
    public ResponseEntity<ParkingMeterResponseDTO> startParkingMeter(@RequestBody @Valid ParkingStartDTO parkingStartDTO) {

        ParkingMeterResponseDTO startParkingMeterStatus = parkingMeterService.startParkingMeter(parkingStartDTO);

        return ResponseEntity.ok(startParkingMeterStatus);
    }
}