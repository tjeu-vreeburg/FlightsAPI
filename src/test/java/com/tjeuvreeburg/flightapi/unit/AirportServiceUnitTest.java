package com.tjeuvreeburg.flightapi.unit;

import com.tjeuvreeburg.flightapi.models.dto.AirportDto;
import com.tjeuvreeburg.flightapi.models.entities.Airport;
import com.tjeuvreeburg.flightapi.base.exceptions.ConflictException;
import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.models.mappers.AirportMapper;
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

    @Mock
    private AirportMapper airportMapper;

    @InjectMocks
    private AirportService airportService;

    private Airport airport;
    private AirportDto airportDto;

    @BeforeEach
    public void setupData() {
        airport = DataHelper.createAirportEntity(1L);
        airportDto = DataHelper.createAirportDto(1L);
    }

    @Test
    public void verifyAirportExists() {
        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        when(airportMapper.toDto(airport)).thenReturn(airportDto);

        var result = airportService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Airport 1", result.name());
        assertEquals("Test City 1", result.city());
        verify(airportRepository, times(1)).findById(1L);
        verify(airportMapper, times(1)).toDto(airport);
    }

    @Test
    public void verifyAirportSaved() {
        var newAirportEntity = DataHelper.createAirportEntity(0L);
        var savedAirportEntity = DataHelper.createAirportEntity(1L);

        when(airportMapper.toEntity(airportDto)).thenReturn(newAirportEntity);
        when(airportRepository.saveAndFlush(newAirportEntity)).thenReturn(savedAirportEntity);
        when(airportMapper.toDto(savedAirportEntity)).thenReturn(airportDto);

        var result = airportService.save(airportDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(airportMapper).toEntity(airportDto);
        verify(airportRepository).saveAndFlush(newAirportEntity);
        verify(airportMapper).toDto(savedAirportEntity);
    }

    @Test
    public void verifyAirportUpdated() {
        var updateDto = DataHelper.createAirportDto(2L);
        updateDto = new AirportDto(
                1L,
                "Updated Airport",
                "Updated City",
                "Updated Country",
                "UPD",
                "UPDT"
        );

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        when(airportRepository.saveAndFlush(any(Airport.class))).thenReturn(airport);
        when(airportMapper.toDto(airport)).thenReturn(updateDto);

        var result = airportService.update(1L, updateDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Updated Airport", result.name());
        assertEquals("Updated City", result.city());

        ArgumentCaptor<Airport> captor = ArgumentCaptor.forClass(Airport.class);
        verify(airportRepository).saveAndFlush(captor.capture());
        var capturedAirport = captor.getValue();
        assertEquals("Updated Airport", capturedAirport.getName());
        assertEquals("Updated City", capturedAirport.getCity());

        verify(airportRepository).findById(1L);
        verify(airportMapper).toDto(airport);
    }

    @Test
    public void verifyAirportsHaveBeenFound() {
        var page = new PageImpl<>(List.of(airport));
        when(airportRepository.findAll(any(AirportSpecification.class), any(PageRequest.class))).thenReturn(page);
        when(airportMapper.toDto(airport)).thenReturn(airportDto);

        var airportSpecification = AirportSpecification.filter("Test City 1", "Test Country 1");
        var result = airportService.findAll(airportSpecification, PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals("Test Airport 1", result.getContent().get(0).name());
        verify(airportRepository, times(1)).findAll(any(AirportSpecification.class), any(PageRequest.class));
        verify(airportMapper, times(1)).toDto(airport);
    }

    @Test
    public void verifyAirportDeleted() {
        when(flightRepository.existsByOriginId(1L)).thenReturn(false);
        when(flightRepository.existsByDestinationId(1L)).thenReturn(false);
        doNothing().when(airportRepository).deleteById(1L);

        airportService.delete(1L);

        verify(flightRepository).existsByOriginId(1L);
        verify(flightRepository).existsByDestinationId(1L);
        verify(airportRepository).deleteById(1L);
    }

    @Test
    public void verifyAirportCannotBeDeletedBecauseOfOriginFlights() {
        when(flightRepository.existsByOriginId(1L)).thenReturn(true);

        var ex = assertThrows(ConflictException.class, () -> airportService.delete(1L));
        assertTrue(ex.getMessage().contains("Cannot delete airport with existing flights"));

        verify(flightRepository).existsByOriginId(1L);
        verify(airportRepository, never()).deleteById(1L);
    }

    @Test
    public void verifyAirportCannotBeDeletedBecauseOfDestinationFlights() {
        when(flightRepository.existsByOriginId(1L)).thenReturn(false);
        when(flightRepository.existsByDestinationId(1L)).thenReturn(true);

        var ex = assertThrows(ConflictException.class, () -> airportService.delete(1L));
        assertTrue(ex.getMessage().contains("Cannot delete airport with existing flights"));

        verify(flightRepository).existsByOriginId(1L);
        verify(flightRepository).existsByDestinationId(1L);
        verify(airportRepository, never()).deleteById(1L);
    }

    @Test
    public void verifyResourceNotFoundException() {
        when(airportRepository.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> airportService.getById(99L));
        assertTrue(ex.getMessage().contains("Could not find airport with id: 99"));

        verify(airportRepository).findById(99L);
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenAirportNotFound() {
        when(airportRepository.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class,
                () -> airportService.update(99L, airportDto));
        assertTrue(ex.getMessage().contains("Could not find airport with id: 99"));

        verify(airportRepository).findById(99L);
        verify(airportRepository, never()).saveAndFlush(any(Airport.class));
    }
}