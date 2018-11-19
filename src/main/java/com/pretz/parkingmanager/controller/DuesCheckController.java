package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.dto.DuesRequestDTO;
import com.pretz.parkingmanager.dto.DuesResponseDTO;
import com.pretz.parkingmanager.service.DuesCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "/dues", tags = "Checking dues for vehicle (driver)")
@RestController
@RequestMapping("/dues/")
@RequiredArgsConstructor
public class DuesCheckController {

    private final DuesCheckService duesCheckService;

    @ApiOperation(value = "Check present due for a parked vehicle")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currencyCode", value = "Code of currency used in dues calculation", required = true, defaultValue = "PLN"),
            @ApiImplicitParam(name = "parkingSessionId", value = "Id of parking session (gained on parking meter start)", dataType = "long", required = true),
            @ApiImplicitParam(name = "vehicleId", value = "Id of parked vehicle", required = true)
    })
    @GetMapping("check")
    public ResponseEntity<DuesResponseDTO> checkDues(@Valid DuesRequestDTO duesRequestDTO) {

        return ResponseEntity.ok(duesCheckService.checkDues(duesRequestDTO));
    }

    @ApiOperation(value = "Check due after just stopping parking meter for a vehicle")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id of parking session", required = true, dataType = "long"),
            @ApiImplicitParam(name = "currencyCode", value = "Code of currency used in dues calculation", required = true, defaultValue = "PLN")
    })
    @GetMapping("check/{id}")
    public ResponseEntity<DuesResponseDTO> checkDues(@PathVariable long id, @RequestParam String currencyCode) {

        return ResponseEntity.ok(duesCheckService.checkDues(id, currencyCode));
    }
}
