package com.tjeuvreeburg.flightapi.unit;

import com.tjeuvreeburg.flightapi.entities.Booking;
import com.tjeuvreeburg.flightapi.entities.Flight;
import com.tjeuvreeburg.flightapi.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.repositories.BookingRepository;
import com.tjeuvreeburg.flightapi.services.BookingService;
import com.tjeuvreeburg.flightapi.utilities.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
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

    @InjectMocks
    private BookingService bookingService;

    private Flight flight;
    private Booking booking;

    @BeforeEach
    public void setupData() {
        var origin = DataHelper.createAirport(1L);
        var destination = DataHelper.createAirport(2L);

        flight = DataHelper.createFlight(1L, origin, destination);
        booking = DataHelper.createBooking(1L, flight);
    }

    @Test
    public void verifyBookingExists() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking result = bookingService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getFlightId());
        assertEquals("Test First Name 1", result.getFirstName());
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyBookingUpdated() {
        Booking newBooking = DataHelper.createBooking(2L, flight);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.saveAndFlush(booking)).thenReturn(newBooking);

        Booking result = bookingService.update(1L, newBooking);

        assertEquals("Test First Name 2", result.getFirstName());
        assertEquals("Test Seat 2", result.getSeat());

        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).saveAndFlush(captor.capture());
    }

    @Test
    public void verifyBookingsHaveBeenFound() {
        Page<Booking> page = new PageImpl<>(List.of(booking));
        when(bookingRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<Booking> result = bookingService.findAll(PageRequest.of(0, 10));

        assertEquals(1, result.getSize());
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

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> bookingService.getById(99L));
        assertTrue(ex.getMessage().contains("Could not find booking with id: 99"));

        verify(bookingRepository).findById(99L);
    }
}