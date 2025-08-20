package com.tjeuvreeburg.flightapi.unit;

import com.tjeuvreeburg.flightapi.models.dto.AirportDto;
import com.tjeuvreeburg.flightapi.models.dto.BookingDto;
import com.tjeuvreeburg.flightapi.models.dto.FlightDto;
import com.tjeuvreeburg.flightapi.models.dto.PassengerDto;
import com.tjeuvreeburg.flightapi.models.entities.Airport;
import com.tjeuvreeburg.flightapi.models.entities.Booking;
import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.models.entities.Flight;
import com.tjeuvreeburg.flightapi.models.entities.Passenger;
import com.tjeuvreeburg.flightapi.models.mappers.BookingMapper;
import com.tjeuvreeburg.flightapi.repositories.BookingRepository;
import com.tjeuvreeburg.flightapi.repositories.FlightRepository;
import com.tjeuvreeburg.flightapi.repositories.PassengerRepository;
import com.tjeuvreeburg.flightapi.services.BookingService;
import com.tjeuvreeburg.flightapi.utilities.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceUnitTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingService bookingService;

    private Airport origin;
    private Airport destination;
    private Booking booking;
    private Flight flight;
    private Passenger passenger;

    private AirportDto originDto;
    private AirportDto destinationDto;
    private BookingDto bookingDto;
    private FlightDto flightDto;
    private PassengerDto passengerDto;

    @BeforeEach
    public void setupData() {
        originDto = DataHelper.createAirportDto(1L);
        destinationDto = DataHelper.createAirportDto(2L);

        flightDto = DataHelper.createFlightDto(1L, originDto, destinationDto);
        passengerDto = DataHelper.createPassengerDto(1L);
        bookingDto = DataHelper.createBookingDto(1L, flightDto, passengerDto);

        origin = DataHelper.createAirportEntity(1L);
        destination = DataHelper.createAirportEntity(2L);
        flight = DataHelper.createFlightEntity(1L, origin, destination);
        passenger = DataHelper.createPassengerEntity(1L);
        booking = DataHelper.createBookingEntity(1L, flight, passenger);
    }

    @Test
    public void verifyBookingExists() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingMapper.toDto(booking)).thenReturn(bookingDto);

        var result = bookingService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(flightDto.id(), result.flight().id());
        assertEquals(passengerDto.id(), result.passenger().id());
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyBookingUpdated() {
        var originEntity = DataHelper.createAirportEntity(1L);
        var destinationEntity = DataHelper.createAirportEntity(2L);
        var newFlightEntity = DataHelper.createFlightEntity(2L, originEntity, destinationEntity);
        var newPassengerEntity = DataHelper.createPassengerEntity(2L);

        var updateFlightDto = DataHelper.createFlightDto(2L, originDto, destinationDto);
        var updatePassengerDto = DataHelper.createPassengerDto(2L);
        var updateBookingDto = DataHelper.createBookingDto(1L, updateFlightDto, updatePassengerDto);

        var updatedBooking = DataHelper.createBookingEntity(1L, newFlightEntity, newPassengerEntity);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(flightRepository.findById(2L)).thenReturn(Optional.of(newFlightEntity));
        when(passengerRepository.findById(2L)).thenReturn(Optional.of(newPassengerEntity));
        when(bookingRepository.saveAndFlush(any(Booking.class))).thenReturn(updatedBooking);
        when(bookingMapper.toDto(updatedBooking)).thenReturn(updateBookingDto);

        var result = bookingService.update(1L, updateBookingDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(2L, result.flight().id());
        assertEquals(2L, result.passenger().id());

        verify(bookingRepository).findById(1L);
        verify(flightRepository).findById(2L);
        verify(passengerRepository).findById(2L);
        verify(bookingRepository).saveAndFlush(any(Booking.class));
    }

    @Test
    public void verifyBookingSaved() {
        var newBookingEntity = DataHelper.createBookingEntity(0L, flight, passenger);

        when(bookingMapper.toEntity(bookingDto)).thenReturn(newBookingEntity);
        when(flightRepository.findById(1L)).thenReturn(Optional.of(booking.getFlight()));
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(booking.getPassenger()));
        when(bookingRepository.saveAndFlush(any(Booking.class))).thenReturn(booking);
        when(bookingMapper.toDto(booking)).thenReturn(bookingDto);

        var result = bookingService.save(bookingDto);

        assertNotNull(result);
        assertEquals(1L, result.id());

        verify(bookingMapper).toEntity(bookingDto);
        verify(bookingRepository).saveAndFlush(any(Booking.class));
        verify(bookingMapper).toDto(booking);
    }

    @Test
    public void verifyBookingsHaveBeenFound() {
        var page = new PageImpl<>(List.of(booking));
        when(bookingRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(bookingMapper.toDto(booking)).thenReturn(bookingDto);

        var result = bookingService.findAll(PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals(0, result.getNumber());
        verify(bookingRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void verifyBookingsHasBeenDeleted() {
        doNothing().when(bookingRepository).deleteById(1L);

        bookingService.delete(1L);

        verify(bookingRepository, times(1)).deleteById(1L);
    }

    @Test
    public void verifyResourceNotFoundException() {
        when(bookingRepository.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> bookingService.getById(99L));
        assertTrue(ex.getMessage().contains("Could not find booking with id: 99"));

        verify(bookingRepository).findById(99L);
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenBookingNotFound() {
        when(bookingRepository.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> bookingService.update(99L, bookingDto));
        assertTrue(ex.getMessage().contains("Could not find booking with id: 99"));

        verify(bookingRepository).findById(99L);
        verify(bookingRepository, never()).saveAndFlush(any(Booking.class));
    }

    @Test
    public void verifySaveThrowsExceptionWhenFlightNotFound() {
        var unknownFlightEntity = DataHelper.createFlightEntity(99L, origin, destination);
        var newBookingEntity = DataHelper.createBookingEntity(0L, unknownFlightEntity, passenger);

        when(bookingMapper.toEntity(bookingDto)).thenReturn(newBookingEntity);
        when(flightRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> bookingService.save(bookingDto));
        assertTrue(ex.getMessage().contains("Could not find flight with id: 99"));

        verify(flightRepository).findById(99L);
        verify(bookingRepository, never()).saveAndFlush(any(Booking.class));
    }
}