package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.service.VehicleStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicle/")
@RequiredArgsConstructor
public class VehicleStatusController {

    private final VehicleStatusService vehicleStatusService;

    @GetMapping("status")
    public ResponseEntity<Boolean> isParkingMeterStarted(@RequestParam String vehicleId) {
        return ResponseEntity.ok(vehicleStatusService.checkVehicleStatus(vehicleId));
    }
}
