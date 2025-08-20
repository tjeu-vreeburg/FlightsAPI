package com.tjeuvreeburg.flightapi.models.dto;

import com.tjeuvreeburg.flightapi.base.interfaces.GenericDto;
import jakarta.validation.constraints.NotNull;

public record FlightDto(
        Long id,

        @NotNull(message = "Flight number is required")
        Integer number,

        @NotNull(message = "Origin airport id is required")
        AirportDto origin,

        @NotNull(message = "Destination airport id is required")
        AirportDto destination
) implements GenericDto {

    public FlightDto() {
        this(null, null, null, null);
    }
}