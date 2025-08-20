package com.tjeuvreeburg.flightapi.models.dto;

import com.tjeuvreeburg.flightapi.base.interfaces.GenericDto;
import com.tjeuvreeburg.flightapi.models.entities.Airport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AirportDto(
        Long id,

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Country is required")
        String country,

        @NotBlank(message = "IATA Code is required")
        @Size(min = 3, max = 3, message = "IATA must be exactly 3 characters")
        String iata,

        @NotBlank(message = "ICAO Code is required")
        @Size(min = 4, max = 4, message = "ICAO must be exactly 4 characters")
        String icao
) implements GenericDto {

    public AirportDto() {
        this(null, null, null, null, null, null);
    }

    public AirportDto(Airport airport) {
        this(airport.getId(), airport.getName(), airport.getCity(), airport.getCountry(), airport.getIata(), airport.getIcao());
    }
}