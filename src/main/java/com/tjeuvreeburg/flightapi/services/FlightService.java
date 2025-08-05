package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.entities.Flight;
import com.tjeuvreeburg.flightapi.repositories.AirportRepository;
import com.tjeuvreeburg.flightapi.repositories.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FlightService implements IService<Flight> {

    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    public FlightService(AirportRepository airportRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    public List<Flight> findFlightsWithAirport(Long id) {
        return flightRepository.findFlightsWithAirport(id);
    }

    @Override
    @Transactional
    public void delete(long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public Flight getById(long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find flight with id: " + id));
    }

    @Override
    @Transactional
    public Flight save(Flight flight) {
        var originId = flight.getOriginId();
        if (originId != null) {
            var origin = airportRepository.findById(originId).orElseThrow(() ->
                    new ResourceNotFoundException("Origin airport not found with id: " + originId)
            );

            flight.setOrigin(origin);
        }

        var destinationId = flight.getDestinationId();
        if (destinationId != null) {
            var destination = airportRepository.findById(destinationId).orElseThrow(() ->
                    new ResourceNotFoundException("Destination airport not found with id: " + destinationId)
            );

            flight.setDestination(destination);
        }

        return flightRepository.saveAndFlush(flight);
    }

    @Override
    public Flight update(long id, Flight newFlight) {
        return flightRepository.findById(id)
                .map(flight -> {
                    flight.setNumber(newFlight.getNumber());
                    flight.setDestination(newFlight.getDestination());
                    flight.setOrigin(newFlight.getOrigin());
                    return flightRepository.saveAndFlush(flight);
                })
                .orElseGet(() -> flightRepository.saveAndFlush(newFlight));
    }

    @Override
    public Page<Flight> findAll(Pageable pageable) {
        return flightRepository.findAll(pageable);
    }
}