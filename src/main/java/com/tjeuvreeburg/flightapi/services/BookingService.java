package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.base.generics.GenericService;
import com.tjeuvreeburg.flightapi.entities.Booking;
import com.tjeuvreeburg.flightapi.repositories.BookingRepository;
import com.tjeuvreeburg.flightapi.repositories.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookingService implements GenericService<Booking, Specification<Booking>> {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    public BookingService(BookingRepository bookingRepository, FlightRepository flightRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    @Transactional
    public void delete(long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking getById(long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("booking", id));
    }

    @Override
    @Transactional
    public Booking save(Booking booking) {
        var flightId = booking.getFlightId();
        if (flightId != null) {
            var flight = flightRepository.findById(flightId)
                    .orElseThrow(() -> ResourceNotFoundException.of("flight", flightId));

            booking.setFlight(flight);
        }
        return bookingRepository.saveAndFlush(booking);
    }

    @Override
    @Transactional
    public Booking update(long id, Booking newBooking) {
        return bookingRepository.findById(id)
                .map(booking -> {
                    booking.setFirstName(newBooking.getFirstName());
                    booking.setLastName(newBooking.getLastName());
                    booking.setFlight(newBooking.getFlight());
                    booking.setCabinClass(newBooking.getCabinClass());
                    booking.setSeat(booking.getSeat());
                    return bookingRepository.saveAndFlush(booking);
                })
                .orElseThrow(() -> ResourceNotFoundException.of("booking", id));
    }

    @Override
    public Page<Booking> findAll(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }
}