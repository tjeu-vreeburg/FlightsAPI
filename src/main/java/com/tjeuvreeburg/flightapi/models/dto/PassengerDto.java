package com.tjeuvreeburg.flightapi.models.dto;

import com.tjeuvreeburg.flightapi.base.interfaces.GenericDto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PassengerDto(
        Long id,
        @NotNull(message = "Address is required")
        AddressDto address,

        @NotNull(message = "First name is required")
        String firstName,

        @NotNull(message = "Middle name is required")
        String middleName,

        @NotNull(message = "Last name is required")
        String lastName,

        @NotNull(message = "Date of birth is required")
        LocalDate dateOfBirth
) implements GenericDto {

    public PassengerDto() {
        this(null, null, null, null, null, null);
    }
}