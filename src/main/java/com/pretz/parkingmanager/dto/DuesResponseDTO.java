package com.pretz.parkingmanager.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@RequiredArgsConstructor
@Builder
public final class DuesResponseDTO {

    private final Timestamp parkingStartTime;
    private final BigDecimal dues;
}
