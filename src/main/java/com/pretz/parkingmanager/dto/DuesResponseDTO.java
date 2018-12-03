package com.pretz.parkingmanager.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pretz.parkingmanager.dto.serializer.MoneySerializer;
import com.pretz.parkingmanager.dto.serializer.TimestampSerializer;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
public final class DuesResponseDTO {

    @Getter
    @JsonSerialize(using = TimestampSerializer.class)
    private final Timestamp parkingStartTime;

    @Getter
    @JsonSerialize(using = MoneySerializer.class)
    private final BigDecimal dues;
}
