package com.tjeuvreeburg.flightapi.models.dto;

import com.tjeuvreeburg.flightapi.base.interfaces.GenericDto;
import com.tjeuvreeburg.flightapi.models.entities.Address;
import jakarta.validation.constraints.NotBlank;

public record AddressDto(
        Long id,

        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Country is required")
        String country,

        @NotBlank(message = "PostalCode is required")
        String postalCode
) implements GenericDto {

    public AddressDto() {
        this(null, null, null, null, null);
    }

    public AddressDto(Address address) {
        this(address.getId(), address.getStreet(), address.getCity(), address.getCountry(), address.getPostalCode());
    }
}