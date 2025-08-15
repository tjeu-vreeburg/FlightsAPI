package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.base.exceptions.ConflictException;
import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.base.generics.GenericService;
import com.tjeuvreeburg.flightapi.entities.Flight;
import com.tjeuvreeburg.flightapi.repositories.AirportRepository;
import com.tjeuvreeburg.flightapi.repositories.BookingRepository;
import com.tjeuvreeburg.flightapi.repositories.FlightRepository;
import com.tjeuvreeburg.flightapi.specifications.FlightSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FlightService implements GenericService<Flight, FlightSpecification> {

    private final AirportRepository airportRepository;
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    public FlightService(AirportRepository airportRepository, BookingRepository bookingRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (bookingRepository.existsByFlightId(id)) {
            throw new ConflictException("cancel", "flight", "bookings");
        }

        flightRepository.deleteById(id);
    }

    @Override
    public Flight getById(long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("flight", id));
    }

    @Override
    @Transactional
    public Flight save(Flight flight) {
        var originId = flight.getOriginId();
        if (originId != null) {
            var origin = airportRepository.findById(originId)
                    .orElseThrow(() -> ResourceNotFoundException.of("origin airport", originId));

            flight.setOrigin(origin);
        }

        var destinationId = flight.getDestinationId();
        if (destinationId != null) {
            var destination = airportRepository.findById(destinationId)
                    .orElseThrow(() -> ResourceNotFoundException.of("destination airport", destinationId));

            flight.setDestination(destination);
        }

        return flightRepository.saveAndFlush(flight);
    }

    @Override
    @Transactional
    public Flight update(long id, Flight newFlight) {
        return flightRepository.findById(id)
                .map(flight -> {
                    flight.setNumber(newFlight.getNumber());
                    flight.setDestination(newFlight.getDestination());
                    flight.setOrigin(newFlight.getOrigin());
                    return flightRepository.saveAndFlush(flight);
                })
                .orElseThrow(() -> ResourceNotFoundException.of("flight", id));
    }

    @Override
    public Page<Flight> findAll(FlightSpecification specification, Pageable pageable) {
        return flightRepository.findAll(specification, pageable);
    }
}