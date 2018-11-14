package com.pretz.parkingmanager.controller;

import com.pretz.parkingmanager.dto.DuesRequestDTO;
import com.pretz.parkingmanager.dto.DuesResponseDTO;
import com.pretz.parkingmanager.service.DuesCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/dues/")
@RequiredArgsConstructor
public class DuesCheckController {

    private final DuesCheckService duesCheckService;

    @GetMapping("check-dues")
    public ResponseEntity<DuesResponseDTO> checkDues(@Valid DuesRequestDTO duesRequestDTO) {

        return ResponseEntity.ok(duesCheckService.checkDues(duesRequestDTO));
    }
}
