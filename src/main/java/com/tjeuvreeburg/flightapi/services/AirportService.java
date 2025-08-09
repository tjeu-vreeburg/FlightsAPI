package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.base.exceptions.ConflictException;
import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.base.generics.GenericService;
import com.tjeuvreeburg.flightapi.entities.Airport;
import com.tjeuvreeburg.flightapi.repositories.AirportRepository;
import com.tjeuvreeburg.flightapi.repositories.FlightRepository;
import com.tjeuvreeburg.flightapi.specifications.AirportSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AirportService implements GenericService<Airport, AirportSpecification> {

    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    public AirportService(AirportRepository airportRepository, FlightRepository flightRepository) {
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
    public Airport getById(long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("airport", id));
    }


    @Override
    @Transactional
    public Airport save(Airport airport) {
        return airportRepository.saveAndFlush(airport);
    }

    @Override
    public Airport update(long id, Airport newAirport) {
        return airportRepository.findById(id)
                .map(airport -> {
                    airport.setName(newAirport.getName());
                    airport.setCountry(newAirport.getCountry());
                    airport.setCity(newAirport.getCity());
                    airport.setIata(newAirport.getIata());
                    airport.setIcao(newAirport.getIcao());
                    return airportRepository.saveAndFlush(airport);
                })
                .orElseThrow(() -> ResourceNotFoundException.of("airport", id));
    }

    @Override
    public Page<Airport> findAll(AirportSpecification specification, Pageable pageable) {
        return airportRepository.findAll(specification, pageable);
    }
}