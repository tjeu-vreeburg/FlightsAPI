package com.tjeuvreeburg.flightapi.unit;

import com.tjeuvreeburg.flightapi.entities.Airport;
import com.tjeuvreeburg.flightapi.entities.Flight;
import com.tjeuvreeburg.flightapi.base.exceptions.ConflictException;
import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.repositories.BookingRepository;
import com.tjeuvreeburg.flightapi.repositories.FlightRepository;
import com.tjeuvreeburg.flightapi.services.FlightService;
import com.tjeuvreeburg.flightapi.specifications.FlightSpecification;
import com.tjeuvreeburg.flightapi.utilities.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceUnitTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private Airport origin;
    private Airport destination;
    private Flight flight;

    @BeforeEach
    public void setupData() {
        origin = DataHelper.createAirport(1L);
        destination = DataHelper.createAirport(2L);
        flight = DataHelper.createFlight(1L, origin, destination);
    }

    @Test
    public void verifyFlightExists() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        var result = flightService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getOriginId());
        assertEquals(2L, result.getDestinationId());
        verify(flightRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyFlightUpdated() {
        var newFlight = DataHelper.createFlight(2L, origin, destination);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightRepository.saveAndFlush(flight)).thenReturn(newFlight);

        var result = flightService.update(1L, newFlight);

        assertEquals(3, result.getNumber());

        var captor = ArgumentCaptor.forClass(Flight.class);
        verify(flightRepository).saveAndFlush(captor.capture());
    }

    @Test
    public void verifyFlightsHaveBeenFound() {
        var page = new PageImpl<>(List.of(flight));
        when(flightRepository.findAll(any(FlightSpecification.class), any(PageRequest.class))).thenReturn(page);

        var flightSpecification = new FlightSpecification("Test City 1", "Test City 2");
        var result = flightService.findAll(flightSpecification, PageRequest.of(0, 10));

        assertEquals(1, result.getSize());
        assertEquals(0, result.getNumber());
        verify(flightRepository, times(1)).findAll(any(FlightSpecification.class), any(PageRequest.class));
    }

    @Test
    public void verifyFlightHasBeenDeleted() {
        when(bookingRepository.existsByFlightId(1L)).thenReturn(true);

        ConflictException ex = assertThrows(ConflictException.class, () -> flightService.delete(1L));
        assertTrue(ex.getMessage().contains("Cannot delete flight with existing bookings."));

        verify(flightRepository, times(0)).deleteById(1L);
    }

    @Test
    public void verifyResourceNotFoundException() {
        when(flightRepository.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> flightService.getById(99L));
        assertTrue(ex.getMessage().contains("Could not find flight with id: 99"));

        verify(flightRepository).findById(99L);
    }
}