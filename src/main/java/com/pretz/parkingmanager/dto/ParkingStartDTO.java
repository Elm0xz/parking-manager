package com.pretz.parkingmanager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder(builderClassName = "ParkingStartRequestDTOBuilder")
@JsonDeserialize(builder = ParkingStartDTO.ParkingStartRequestDTOBuilder.class)
public class ParkingStartDTO {

    private static final long PARKING_RATES_NR = 2;
    private static final String BASIC_VEH_ID_REGEX = "[A-Z]{3}[0-9]{4}";

    @NotNull
    @Pattern(regexp = BASIC_VEH_ID_REGEX)
    private final String vehicleId;

    @NotNull
    @Min(value = 1)
    @Max(value = PARKING_RATES_NR)
    private final long parkingRateId;

    @JsonPOJOBuilder(withPrefix = "")
    static class ParkingStartRequestDTOBuilder {
    }
}
