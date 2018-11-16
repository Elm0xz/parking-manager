package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.service.EarningsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/earnings/")
@RequiredArgsConstructor
public class EarningsController {

    private final EarningsService earningsService;

    @GetMapping("check")
    //TODO how to solve issue with big decimal format?
    //@JsonSerialize(using = MoneySerializer.class)
    public ResponseEntity<BigDecimal> checkEarnings(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

        BigDecimal earnings = earningsService.checkEarnings(date);
        return ResponseEntity.ok(earnings);
    }
}
