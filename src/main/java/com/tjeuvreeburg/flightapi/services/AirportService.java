package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.base.exceptions.ConflictException;
import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericSpecificationService;
import com.tjeuvreeburg.flightapi.models.dto.AirportDto;
import com.tjeuvreeburg.flightapi.models.entities.Airport;
import com.tjeuvreeburg.flightapi.models.mappers.AirportMapper;
import com.tjeuvreeburg.flightapi.repositories.AirportRepository;
import com.tjeuvreeburg.flightapi.repositories.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AirportService implements GenericSpecificationService<Airport, AirportDto> {

    private final AirportMapper airportMapper;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    public AirportService(
            AirportMapper airportMapper,
            AirportRepository airportRepository,
            FlightRepository flightRepository
    ) {
        this.airportMapper = airportMapper;
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (flightRepository.existsByOriginId(id)) {
            throw new ConflictException("delete", "airport", "flights");
        }

        if (flightRepository.existsByDestinationId(id)) {
            throw new ConflictException("delete", "airport", "flights");
        }

        airportRepository.deleteById(id);
    }

    @Override
    public AirportDto getById(long id) {
        return airportRepository.findById(id)
                .map(airportMapper::toDto)
                .orElseThrow(() -> ResourceNotFoundException.of("airport", id));
    }


    @Override
    @Transactional
    public AirportDto save(AirportDto airportDto) {
        var airport = airportMapper.toEntity(airportDto);
        var save = airportRepository.saveAndFlush(airport);
        return airportMapper.toDto(save);
    }

    @Override
    @Transactional
    public AirportDto update(long id, AirportDto airportDto) {
        return airportRepository.findById(id)
                .map(airport -> {
                    airport.setName(airportDto.name());
                    airport.setCity(airportDto.city());
                    airport.setCountry(airportDto.country());
                    airport.setIata(airportDto.iata());
                    airport.setIcao(airportDto.icao());

                    var savedAirport = airportRepository.saveAndFlush(airport);
                    return airportMapper.toDto(savedAirport);
                })
                .orElseThrow(() -> ResourceNotFoundException.of("airport", id));
    }

    @Override
    public Page<AirportDto> findAll(Specification<Airport> specification, Pageable pageable) {
        return airportRepository.findAll(specification, pageable).map(airportMapper::toDto);
    }
}