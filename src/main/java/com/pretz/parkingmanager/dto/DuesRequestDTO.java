package com.pretz.parkingmanager.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.pretz.parkingmanager.dto.ValidationPatterns.BASIC_VEH_ID_REGEX;
import static com.pretz.parkingmanager.dto.ValidationPatterns.CURRENCY_REGEX;

@Getter
@Setter
@RequiredArgsConstructor
public final class DuesRequestDTO {

    @NotNull
    @Pattern(regexp = BASIC_VEH_ID_REGEX)
    private final String vehicleId;

    @Min(value = 1)
    private final long parkingSessionId;

    @NotNull
    @Pattern(regexp = CURRENCY_REGEX)
    private final String currencyCode;
}
