package com.tjeuvreeburg.flightapi.models.mappers;

import com.tjeuvreeburg.flightapi.base.abstraction.AbstractMapper;
import com.tjeuvreeburg.flightapi.models.dto.AddressDto;
import com.tjeuvreeburg.flightapi.models.entities.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper extends AbstractMapper<Address, AddressDto> {

    @Override
    public Address toEntity(AddressDto addressDto) {
        return new Address(addressDto);
    }

    @Override
    public AddressDto toDto(Address address) {
        return new AddressDto(address);
    }
}