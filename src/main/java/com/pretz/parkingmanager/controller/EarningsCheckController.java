package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.dto.EarningsResponseDTO;
import com.pretz.parkingmanager.service.EarningsCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Api(value = "/earnings", tags = "Checking earnings (parking owner)")
@RestController
@RequestMapping("/earnings/")
@RequiredArgsConstructor
public class EarningsCheckController {

    private final EarningsCheckService earningsCheckService;

    @ApiOperation(value = "Check earnings from a given day")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "date", value = "Date checked for earnings in YYYY-MM-DD format", paramType = "query", required = true)
    })
    @GetMapping()
    public ResponseEntity<EarningsResponseDTO> checkEarnings(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

        return ResponseEntity.ok(earningsCheckService.checkEarnings(date));
    }
}
