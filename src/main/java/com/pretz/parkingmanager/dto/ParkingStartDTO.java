package com.pretz.parkingmanager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.pretz.parkingmanager.dto.ValidationPatterns.BASIC_VEH_ID_REGEX;
import static com.pretz.parkingmanager.dto.ValidationPatterns.PARKING_RATES_NR;

@Getter
@Builder(builderClassName = "ParkingStartDTOBuilder")
@JsonDeserialize(builder = ParkingStartDTO.ParkingStartDTOBuilder.class)
public final class ParkingStartDTO {

    @NotNull
    @Pattern(regexp = BASIC_VEH_ID_REGEX)
    private final String vehicleId;

    @NotNull
    @Min(value = 1)
    @Max(value = PARKING_RATES_NR)
    private final long parkingRateId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ParkingStartDTOBuilder {
    }
}
