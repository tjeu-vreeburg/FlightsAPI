package com.tjeuvreeburg.flightapi.models.dto;

import com.tjeuvreeburg.flightapi.base.interfaces.GenericDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookingDto(
        Long id,

        @NotNull(message = "Flight is required")
        FlightDto flight,

        @NotNull(message = "Flight is required")
        PassengerDto passenger,

        @NotBlank(message = "Cabin class is required")
        String cabinClass,

        @NotBlank(message = "Seat is required")
        String seat
) implements GenericDto {

    public BookingDto() {
        this(null, null, null, null, null);
    }
}