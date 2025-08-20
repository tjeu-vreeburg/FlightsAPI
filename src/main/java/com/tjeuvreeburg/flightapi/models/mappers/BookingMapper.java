package com.tjeuvreeburg.flightapi.models.mappers;

import com.tjeuvreeburg.flightapi.base.abstraction.AbstractMapper;
import com.tjeuvreeburg.flightapi.models.dto.BookingDto;
import com.tjeuvreeburg.flightapi.models.entities.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper extends AbstractMapper<Booking, BookingDto> {

    private final FlightMapper flightMapper;
    private final PassengerMapper passengerMapper;

    public BookingMapper(FlightMapper flightMapper, PassengerMapper passengerMapper) {
        this.flightMapper = flightMapper;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public Booking toEntity(BookingDto bookingDto) {
        var flight = bookingDto.flight() != null ? flightMapper.toEntity(bookingDto.flight()) : null;
        var passengerDto = bookingDto.passenger() != null ? passengerMapper.toEntity(bookingDto.passenger()) : null;
        var booking = new Booking();

        booking.setId(bookingDto.id());
        booking.setFlight(flight);
        booking.setPassenger(passengerDto);
        booking.setSeat(bookingDto.seat());
        booking.setCabinClass(bookingDto.cabinClass());

        return booking;
    }

    @Override
    public BookingDto toDto(Booking booking) {
        var flight = booking.getFlight();
        var passenger = booking.getPassenger();

        return new BookingDto(
                booking.getId(),
                flight != null ? flightMapper.toDto(flight) : null,
                passenger != null ? passengerMapper.toDto(passenger) : null,
                booking.getCabinClass(),
                booking.getSeat()
        );
    }
}