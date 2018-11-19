package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.dto.ParkingMeterResponseDTO;
import com.pretz.parkingmanager.dto.ParkingStartDTO;
import com.pretz.parkingmanager.dto.ParkingStopDTO;
import com.pretz.parkingmanager.service.ParkingMeterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Api(value = "/parking-meter", tags = "Parking meter (driver)")
@RestController
@RequestMapping("/parking-meter/")
@RequiredArgsConstructor
public class ParkingMeterController {

    private final ParkingMeterService parkingMeterService;

    @ApiOperation(value = "Start parking time for a vehicle")
    @ApiImplicitParam(name = "parkingStartDTO", value = "Vehicle id and parking rate type (1 - regular, 2 - disabled)", dataType = "ParkingStartDTO", required = true)
    @PostMapping("start")
    public ResponseEntity<ParkingMeterResponseDTO> startParkingMeter(@RequestBody @Valid ParkingStartDTO parkingStartDTO) {

        ParkingMeterResponseDTO startParkingMeterStatus = parkingMeterService.startParkingMeter(parkingStartDTO);

        return ResponseEntity.ok(startParkingMeterStatus);
    }

    @ApiOperation(value = "Stop parking time for a vehicle")
    @ApiImplicitParam(name = "parkingStopDTO", value = "Vehicle id, parking session id (from parking start) and parking rate type (1 - regular, 2 - disabled)", dataType = "ParkingStopDTO", required = true)
    @PostMapping("stop")
    public ResponseEntity<ParkingMeterResponseDTO> stopParkingMeter(@RequestBody @Valid ParkingStopDTO parkingStopDTO) {

        ParkingMeterResponseDTO stopParkingMeterStatus = parkingMeterService.stopParkingMeter(parkingStopDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setLocation(createLocationUri(parkingStopDTO));

        return new ResponseEntity<>(stopParkingMeterStatus, headers, HttpStatus.SEE_OTHER);
    }

    private URI createLocationUri(ParkingStopDTO parkingStopDTO) {

        UriComponents uriComponents = UriComponentsBuilder.fromUriString("/dues/check/")
                .path(Long.toString(parkingStopDTO.getParkingSessionId()))
                .queryParam("currencyCode", parkingStopDTO.getCurrencyCode())
                .build();
        return uriComponents.toUri();
    }
}