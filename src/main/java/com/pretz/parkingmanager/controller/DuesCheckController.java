package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.dto.DuesRequestDTO;
import com.pretz.parkingmanager.dto.DuesResponseDTO;
import com.pretz.parkingmanager.service.DuesCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/dues/")
@RequiredArgsConstructor
public class DuesCheckController {

    private final DuesCheckService duesCheckService;

    @GetMapping("check")
    public ResponseEntity<DuesResponseDTO> checkDues(@Valid DuesRequestDTO duesRequestDTO) {

        return ResponseEntity.ok(duesCheckService.checkDues(duesRequestDTO));
    }

    @GetMapping("check/{id}")
    public ResponseEntity<DuesResponseDTO> checkDues(@PathVariable long id, @RequestParam String currencyCode) {

        return ResponseEntity.ok(duesCheckService.checkDues(id, currencyCode));
    }
}
