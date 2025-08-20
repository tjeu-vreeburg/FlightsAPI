package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericService;
import com.tjeuvreeburg.flightapi.models.dto.BookingDto;
import com.tjeuvreeburg.flightapi.models.mappers.BookingMapper;
import com.tjeuvreeburg.flightapi.repositories.BookingRepository;
import com.tjeuvreeburg.flightapi.repositories.FlightRepository;
import com.tjeuvreeburg.flightapi.repositories.PassengerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookingService implements GenericService<BookingDto> {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    public BookingService(
            BookingMapper bookingMapper,
            BookingRepository bookingRepository,
            FlightRepository flightRepository,
            PassengerRepository passengerRepository
    ) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    @Transactional
    public void delete(long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public BookingDto getById(long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toDto)
                .orElseThrow(() -> ResourceNotFoundException.of("booking", id));
    }

    @Override
    @Transactional
    public BookingDto save(BookingDto bookingDto) {
        var booking = bookingMapper.toEntity(bookingDto);

        var flightId = booking.getFlightId();
        if (flightId != null) {
            var flight = flightRepository.findById(flightId)
                    .orElseThrow(() -> ResourceNotFoundException.of("flight", flightId));

            booking.setFlight(flight);
        }

        var passengerId = booking.getPassengerId();
        if (passengerId != null) {
            var passenger = passengerRepository.findById(passengerId)
                    .orElseThrow(() -> ResourceNotFoundException.of("passenger", passengerId));

            booking.setPassenger(passenger);
        }

        var save = bookingRepository.saveAndFlush(booking);
        return bookingMapper.toDto(save);
    }

    @Override
    @Transactional
    public BookingDto update(long id, BookingDto bookingDto) {
        var booking = bookingRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("booking", id));

        if (bookingDto.cabinClass() != null) {
            booking.setCabinClass(bookingDto.cabinClass());
        }

        if (bookingDto.seat() != null) {
            booking.setSeat(bookingDto.seat());
        }

        var passengerDto = bookingDto.passenger();
        if (passengerDto != null && passengerDto.id() != null) {
            var passenger = passengerRepository.findById(passengerDto.id())
                    .orElseThrow(() -> ResourceNotFoundException.of("passenger", passengerDto.id()));
            booking.setPassenger(passenger);
        }

        var flightDto = bookingDto.flight();
        if (flightDto != null && flightDto.id() != null) {
            var flight = flightRepository.findById(flightDto.id())
                    .orElseThrow(() -> ResourceNotFoundException.of("flight", flightDto.id()));
            booking.setFlight(flight);
        }

        var save = bookingRepository.saveAndFlush(booking);
        return bookingMapper.toDto(save);
    }

    @Override
    public Page<BookingDto> findAll(Pageable pageable) {
        return bookingRepository.findAll(pageable).map(bookingMapper::toDto);
    }
}