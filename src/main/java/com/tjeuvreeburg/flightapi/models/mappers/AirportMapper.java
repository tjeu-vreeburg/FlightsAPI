package com.tjeuvreeburg.flightapi.models.mappers;

import com.tjeuvreeburg.flightapi.base.abstraction.AbstractMapper;
import com.tjeuvreeburg.flightapi.models.dto.AirportDto;
import com.tjeuvreeburg.flightapi.models.entities.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper extends AbstractMapper<Airport, AirportDto> {

    @Override
    public Airport toEntity(AirportDto airportDto) {
        return new Airport(airportDto);
    }

    @Override
    public AirportDto toDto(Airport airport) {
        return new AirportDto(airport);
    }
}