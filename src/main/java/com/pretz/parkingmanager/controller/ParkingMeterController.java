package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.dto.ParkingMeterResponseDTO;
import com.pretz.parkingmanager.dto.ParkingStartDTO;
import com.pretz.parkingmanager.dto.ParkingStopDTO;
import com.pretz.parkingmanager.service.ParkingMeterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/parking-meter/")
@RequiredArgsConstructor
public class ParkingMeterController {

    private final ParkingMeterService parkingMeterService;

    @PostMapping("start-parking")
    public ResponseEntity<ParkingMeterResponseDTO> startParkingMeter(@RequestBody @Valid ParkingStartDTO parkingStartDTO) {

        ParkingMeterResponseDTO startParkingMeterStatus = parkingMeterService.startParkingMeter(parkingStartDTO);

        return ResponseEntity.ok(startParkingMeterStatus);
    }

    @PostMapping("stop-parking")
    public ResponseEntity<ParkingMeterResponseDTO> stopParkingMeter(@RequestBody @Valid ParkingStopDTO parkingStopDTO) {

        ParkingMeterResponseDTO stopParkingMeterStatus = parkingMeterService.stopParkingMeter(parkingStopDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setLocation(URI.create("/dues/check-dues/" + parkingStopDTO.getParkingSessionId()));
        //TODO change this url

        return new ResponseEntity<>(stopParkingMeterStatus, headers, HttpStatus.SEE_OTHER);
    }
}