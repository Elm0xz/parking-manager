package com.pretz.parkingmanager.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pretz.parkingmanager.dto.serializer.MoneySerializer;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
public final class EarningsResponseDTO {

    @Getter
    @JsonSerialize(using = MoneySerializer.class)
    private final BigDecimal earnings;
}
