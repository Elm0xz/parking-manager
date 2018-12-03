package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.service.VehicleStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/vehicle-status", tags = "Vehicle status (parking operator)")
@RestController
@RequestMapping("/vehicle-status/")
@RequiredArgsConstructor
public class VehicleStatusController {

    private final VehicleStatusService vehicleStatusService;

    @ApiOperation(value = "Check if vehicle has started a parking meter")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vehicleId", value = "Vehicle Id to be checked", paramType = "query", required = true)
    })
    @GetMapping()
    public ResponseEntity<Boolean> isParkingMeterStarted(@RequestParam String vehicleId) {
        return ResponseEntity.ok(vehicleStatusService.checkVehicleStatus(vehicleId));
    }
}
