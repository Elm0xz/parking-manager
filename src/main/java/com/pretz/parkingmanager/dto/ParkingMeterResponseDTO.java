package com.pretz.parkingmanager.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

import static com.pretz.parkingmanager.dto.ValidationPatterns.BASIC_VEH_ID_REGEX;

@Builder
public final class ParkingMeterResponseDTO {

    @NotNull
    @Pattern(regexp = BASIC_VEH_ID_REGEX)
    @Getter
    private final String vehicleId;

    @NotNull
    @Getter
    private final long parkingSessionId;

    @NotNull
    @Getter
    private final Timestamp timestamp;

}
