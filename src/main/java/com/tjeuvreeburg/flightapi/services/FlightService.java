package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.base.exceptions.ConflictException;
import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericSpecificationService;
import com.tjeuvreeburg.flightapi.models.dto.FlightDto;
import com.tjeuvreeburg.flightapi.models.entities.Flight;
import com.tjeuvreeburg.flightapi.models.mappers.FlightMapper;
import com.tjeuvreeburg.flightapi.repositories.AirportRepository;
import com.tjeuvreeburg.flightapi.repositories.BookingRepository;
import com.tjeuvreeburg.flightapi.repositories.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FlightService implements GenericSpecificationService<Flight, FlightDto> {

    private final FlightMapper flightMapper;
    private final AirportRepository airportRepository;
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    public FlightService(
            FlightMapper flightMapper,
            AirportRepository airportRepository,
            BookingRepository bookingRepository,
            FlightRepository flightRepository
    ) {
        this.flightMapper = flightMapper;
        this.airportRepository = airportRepository;
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (bookingRepository.existsByFlightId(id)) {
            throw new ConflictException("delete", "flight", "bookings");
        }

        flightRepository.deleteById(id);
    }

    @Override
    public FlightDto getById(long id) {
        return flightRepository.findById(id)
                .map(flightMapper::toDto)
                .orElseThrow(() -> ResourceNotFoundException.of("flight", id));
    }

    @Override
    @Transactional
    public FlightDto save(FlightDto flightDto) {
        Flight flight = flightMapper.toEntity(flightDto);

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

        Flight save = flightRepository.saveAndFlush(flight);
        return flightMapper.toDto(save);
    }

    @Override
    @Transactional
    public FlightDto update(long id, FlightDto flightDto) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("flight", id));

        if (flightDto.number() != null) {
            flight.setNumber(flightDto.number());
        }

        var originDto = flightDto.origin();
        if (originDto != null && originDto.id() != null) {
            var origin = airportRepository.findById(originDto.id())
                    .orElseThrow(() -> ResourceNotFoundException.of("airport", originDto.id()));
            flight.setOrigin(origin);
        }

        var destinationDto = flightDto.origin();
        if (destinationDto != null && destinationDto.id() != null) {
            var destination = airportRepository.findById(destinationDto.id())
                    .orElseThrow(() -> ResourceNotFoundException.of("airport", destinationDto.id()));
            flight.setDestination(destination);
        }

        var saved = flightRepository.saveAndFlush(flight);
        return flightMapper.toDto(saved);
    }

    @Override
    public Page<FlightDto> findAll(Specification<Flight> specification, Pageable pageable) {
        return flightRepository.findAll(specification, pageable).map(flightMapper::toDto);
    }
}