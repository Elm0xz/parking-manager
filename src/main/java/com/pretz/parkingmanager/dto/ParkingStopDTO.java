package com.pretz.parkingmanager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder(builderClassName = "ParkingStopDTOBuilder")
@JsonDeserialize(builder = ParkingStopDTO.ParkingStopDTOBuilder.class)
public final class ParkingStopDTO {

    private static final String BASIC_VEH_ID_REGEX = "[A-Z]{3}[0-9]{4}";

    @NotNull
    @Pattern(regexp = BASIC_VEH_ID_REGEX)
    private final String vehicleId;

    @NotNull
    private final long parkingSessionId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ParkingStopDTOBuilder {
    }

}
