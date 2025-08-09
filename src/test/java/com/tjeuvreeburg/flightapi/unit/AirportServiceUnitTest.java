package com.tjeuvreeburg.flightapi.unit;

import com.tjeuvreeburg.flightapi.entities.Airport;
import com.tjeuvreeburg.flightapi.base.exceptions.ConflictException;
import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.repositories.AirportRepository;
import com.tjeuvreeburg.flightapi.repositories.FlightRepository;
import com.tjeuvreeburg.flightapi.services.AirportService;
import com.tjeuvreeburg.flightapi.specifications.AirportSpecification;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirportServiceUnitTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportService airportService;

    private Airport airport;

    @BeforeEach
    public void setupData() {
        airport = DataHelper.createAirport(1L);
    }

    @Test
    public void verifyAirportExists() {
        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));

        var result = airportService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(airportRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyAirportUpdated() {
        Airport newAirport = DataHelper.createAirport(2L);

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        when(airportRepository.saveAndFlush(airport)).thenReturn(newAirport);

        var result = airportService.update(1L, newAirport);

        assertEquals("Test City 2", result.getCity());
        assertEquals("122", result.getIata());

        ArgumentCaptor<Airport> captor = ArgumentCaptor.forClass(Airport.class);
        verify(airportRepository).saveAndFlush(captor.capture());
    }

    @Test
    public void verifyAirportsHaveBeenFound() {
        var page = new PageImpl<>(List.of(airport));

        when(airportRepository.findAll(any(AirportSpecification.class), any(PageRequest.class))).thenReturn(page);

        var airportSpecification = AirportSpecification.filter("Test City 1", "Test country 2");
        var result = airportService.findAll(airportSpecification, PageRequest.of(0, 10));

        assertEquals(1, result.getSize());
        assertEquals(0, result.getNumber());
        verify(airportRepository, times(1)).findAll(any(AirportSpecification.class), any(PageRequest.class));
    }

    @Test
    public void verifyAirportCannotBeBecauseOfOriginDeleted() {
        when(flightRepository.existsByOriginId(1L)).thenReturn(true);

        var ex = assertThrows(ConflictException.class, () -> airportService.delete(1L));
        assertTrue(ex.getMessage().contains("Cannot delete airport with existing flights."));

        verify(airportRepository, never()).deleteById(1L);
    }

    @Test
    public void verifyAirportCannotBeBecauseOfDestinationDeleted() {
        when(flightRepository.existsByDestinationId(2L)).thenReturn(true);

        var ex = assertThrows(ConflictException.class, () -> airportService.delete(2L));
        assertTrue(ex.getMessage().contains("Cannot delete airport with existing flights."));

        verify(airportRepository, never()).deleteById(2L);
    }

    @Test
    public void verifyResourceNotFoundException() {
        when(airportRepository.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> airportService.getById(99L));
        assertTrue(ex.getMessage().contains("Could not find airport with id: 99"));

        verify(airportRepository).findById(99L);
    }
}