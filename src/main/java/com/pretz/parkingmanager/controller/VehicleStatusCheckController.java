package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.service.VehicleStatusCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicle/")
@RequiredArgsConstructor
public class VehicleStatusCheckController {

    private final VehicleStatusCheckService vehicleStatusCheckService;

    @GetMapping("status")
    public ResponseEntity<Boolean> isParkingMeterStarted(@RequestParam String vehicleId) {
        return ResponseEntity.ok(vehicleStatusCheckService.checkVehicleStatus(vehicleId));
    }
}
