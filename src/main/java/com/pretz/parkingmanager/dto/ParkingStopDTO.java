package com.pretz.parkingmanager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.pretz.parkingmanager.dto.ValidationPatterns.BASIC_VEH_ID_REGEX;

@Getter
@Builder(builderClassName = "ParkingStopDTOBuilder")
@JsonDeserialize(builder = ParkingStopDTO.ParkingStopDTOBuilder.class)
public final class ParkingStopDTO {

    @NotNull
    @Pattern(regexp = BASIC_VEH_ID_REGEX)
    private final String vehicleId;

    @NotNull
    private final long parkingSessionId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ParkingStopDTOBuilder {
    }

}
