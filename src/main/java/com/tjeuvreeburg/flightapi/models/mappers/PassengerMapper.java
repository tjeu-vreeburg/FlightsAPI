package com.tjeuvreeburg.flightapi.models.mappers;

import com.tjeuvreeburg.flightapi.base.abstraction.AbstractMapper;
import com.tjeuvreeburg.flightapi.models.dto.PassengerDto;
import com.tjeuvreeburg.flightapi.models.entities.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper extends AbstractMapper<Passenger, PassengerDto> {

    private final AddressMapper addressMapper;

    public PassengerMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public Passenger toEntity(PassengerDto passengerDto) {
        var addressDto = passengerDto.address();
        var passenger = new Passenger();

        passenger.setId(passengerDto.id());
        passenger.setAddress(addressDto != null ? addressMapper.toEntity(addressDto) : null);
        passenger.setFirstName(passengerDto.firstName());
        passenger.setMiddleName(passengerDto.middleName());
        passenger.setLastName(passengerDto.lastName());
        passenger.setDateOfBirth(passengerDto.dateOfBirth());

        return passenger;
    }

    @Override
    public PassengerDto toDto(Passenger passenger) {
        var address = passenger.getAddress();
        return new PassengerDto(
                passenger.getId(),
                address != null ? addressMapper.toDto(address) : null,
                passenger.getFirstName(),
                passenger.getMiddleName(),
                passenger.getLastName(),
                passenger.getDateOfBirth()
        );
    }
}