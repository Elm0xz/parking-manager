package com.pretz.parkingmanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
public final class DuesResponseDTO {

    @Getter
    private final Timestamp parkingStartTime;

    @Getter
    private final BigDecimal dues;
}
