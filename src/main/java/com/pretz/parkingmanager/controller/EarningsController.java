package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.dto.EarningsResponseDTO;
import com.pretz.parkingmanager.service.EarningsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/earnings/")
@RequiredArgsConstructor
public class EarningsController {

    private final EarningsService earningsService;

    @GetMapping("check")
    public ResponseEntity<EarningsResponseDTO> checkEarnings(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

        return ResponseEntity.ok(earningsService.checkEarnings(date));
    }
}
