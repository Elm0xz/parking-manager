package com.pretz.parkingmanager.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.pretz.parkingmanager.dto.ValidationPatterns.BASIC_VEH_ID_REGEX;
import static com.pretz.parkingmanager.dto.ValidationPatterns.CURRENCY_REGEX;

@Getter
@Builder(builderClassName = "ParkingStopDTOBuilder")
@JsonDeserialize(builder = ParkingStopDTO.ParkingStopDTOBuilder.class)
public final class ParkingStopDTO {

    @NotNull
    @Pattern(regexp = BASIC_VEH_ID_REGEX)
    private final String vehicleId;

    @Min(value = 1)
    private final long parkingSessionId;

    @NotNull
    @Pattern(regexp = CURRENCY_REGEX)
    private final String currencyCode;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ParkingStopDTOBuilder {
    }

}
