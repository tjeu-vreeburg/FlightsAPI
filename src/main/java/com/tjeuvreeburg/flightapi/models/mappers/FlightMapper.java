package com.tjeuvreeburg.flightapi.models.mappers;

import com.tjeuvreeburg.flightapi.base.abstraction.AbstractMapper;
import com.tjeuvreeburg.flightapi.models.dto.FlightDto;
import com.tjeuvreeburg.flightapi.models.entities.Flight;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper extends AbstractMapper<Flight, FlightDto> {

    private final AirportMapper airportMapper;

    public FlightMapper(AirportMapper airportMapper) {
        this.airportMapper = airportMapper;
    }

    @Override
    public Flight toEntity(FlightDto flightDto) {
        var origin = flightDto.origin() != null ? airportMapper.toEntity(flightDto.origin()) : null;
        var destination = flightDto.destination() != null ? airportMapper.toEntity(flightDto.destination()) : null;
        var flight = new Flight();

        flight.setId(flightDto.id());
        flight.setNumber(flightDto.number());
        flight.setOrigin(origin);
        flight.setDestination(destination);

        return flight;
    }

    @Override
    public FlightDto toDto(Flight flight) {
        var origin = flight.getOrigin();
        var destination = flight.getDestination();

        return new FlightDto(
                flight.getId(),
                flight.getNumber(),
                origin != null ? airportMapper.toDto(origin) : null,
                destination != null ? airportMapper.toDto(destination) : null
        );
    }
}