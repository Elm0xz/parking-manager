package com.pretz.parkingmanager.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;

@Builder
public class ParkingMeterResponseDTO {

    //TODO refactor to outer constants
    private static final String BASIC_VEH_ID_REGEX = "[A-Z]{3}[0-9]{4}";

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
