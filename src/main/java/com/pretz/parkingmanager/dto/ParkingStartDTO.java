package com.pretz.parkingmanager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

//TODO validation values should be moved somewhere else
@Getter
@Builder(builderClassName = "ParkingStartRequestDTOBuilder")
@JsonDeserialize(builder = ParkingStartDTO.ParkingStartRequestDTOBuilder.class)
public class ParkingStartDTO {

    @NotNull
    @Pattern(regexp = "[A-Z]{3}[0-9]{4}")
    private final String vehicleId;

    @NotNull
    @Min(value = 1)
    @Max(value = 2)
    private final long parkingRateId;

    @JsonPOJOBuilder(withPrefix = "")
    static class ParkingStartRequestDTOBuilder {
    }
}