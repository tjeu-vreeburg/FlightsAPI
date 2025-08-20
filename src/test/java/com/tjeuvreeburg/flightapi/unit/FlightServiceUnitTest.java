package com.tjeuvreeburg.flightapi.unit;

import com.tjeuvreeburg.flightapi.base.exceptions.ConflictException;
import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.models.dto.AirportDto;
import com.tjeuvreeburg.flightapi.models.dto.FlightDto;
import com.tjeuvreeburg.flightapi.models.entities.Airport;
import com.tjeuvreeburg.flightapi.models.entities.Flight;
import com.tjeuvreeburg.flightapi.repositories.BookingRepository;
import com.tjeuvreeburg.flightapi.repositories.FlightRepository;
import com.tjeuvreeburg.flightapi.services.FlightService;
import com.tjeuvreeburg.flightapi.specifications.FlightSpecification;
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
class FlightServiceUnitTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private Airport origin;
    private Airport destination;
    private Flight flight;
    private FlightDto flightDto;

    private AirportDto originDto;
    private AirportDto destinationDto;

    @BeforeEach
    public void setupData() {
        originDto = DataHelper.createAirportDto(1L);
        destinationDto = DataHelper.createAirportDto(2L);
        flightDto = DataHelper.createFlightDto(1L, originDto, destinationDto);

        origin = DataHelper.createAirportEntity(1L);
        destination = DataHelper.createAirportEntity(2L);
        flight = DataHelper.createFlightEntity(1L, origin, destination);
    }

    @Test
    public void verifyFlightExists() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        var result = flightService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(originDto.id(), result.origin().id());
        assertEquals(destinationDto.id(), result.destination().id());
        verify(flightRepository).findById(1L);
    }

    @Test
    public void verifyFlightSaved() {
        var newFlightEntity = DataHelper.createFlightEntity(0L, origin, destination);
        var savedFlightEntity = DataHelper.createFlightEntity(1L, origin, destination);

        when(flightRepository.saveAndFlush(newFlightEntity)).thenReturn(savedFlightEntity);

        var result = flightService.save(flightDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(flightRepository).saveAndFlush(any(Flight.class));
    }

    @Test
    public void verifyFlightUpdated() {
        var updateDto = new FlightDto(1L, 3, originDto, destinationDto);
        var updatedFlightEntity = DataHelper.createFlightEntity(1L, origin, destination);
        updatedFlightEntity.setNumber(3);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightRepository.saveAndFlush(any(Flight.class))).thenReturn(updatedFlightEntity);

        var result = flightService.update(1L, updateDto);

        assertNotNull(result);
        assertEquals(3, result.number());
        verify(flightRepository).findById(1L);
        verify(flightRepository).saveAndFlush(any(Flight.class));
    }

    @Test
    public void verifyFlightsHaveBeenFound() {
        var page = new PageImpl<>(List.of(flight));
        when(flightRepository.findAll(any(FlightSpecification.class), any(PageRequest.class))).thenReturn(page);

        var spec = new FlightSpecification("Test City 1", "Test City 2");
        var result = flightService.findAll(spec, PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals(0, result.getNumber());
        verify(flightRepository).findAll(any(FlightSpecification.class), any(PageRequest.class));
    }

    @Test
    public void verifyFlightDeleted() {
        when(bookingRepository.existsByFlightId(1L)).thenReturn(false);
        doNothing().when(flightRepository).deleteById(1L);

        flightService.delete(1L);

        verify(bookingRepository).existsByFlightId(1L);
        verify(flightRepository).deleteById(1L);
    }

    @Test
    public void verifyFlightCannotBeDeletedDueToBooking() {
        when(bookingRepository.existsByFlightId(1L)).thenReturn(true);

        var ex = assertThrows(ConflictException.class, () -> flightService.delete(1L));
        assertTrue(ex.getMessage().contains("Cannot delete flight with existing bookings."));

        verify(bookingRepository).existsByFlightId(1L);
        verify(flightRepository, never()).deleteById(1L);
    }

    @Test
    public void verifyResourceNotFoundException() {
        when(flightRepository.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> flightService.getById(99L));
        assertTrue(ex.getMessage().contains("Could not find flight with id: 99"));

        verify(flightRepository).findById(99L);
    }

    @Test
    public void verifyUpdateThrowsExceptionWhenFlightNotFound() {
        when(flightRepository.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> flightService.update(99L, flightDto));
        assertTrue(ex.getMessage().contains("Could not find flight with id: 99"));

        verify(flightRepository).findById(99L);
        verify(flightRepository, never()).saveAndFlush(any(Flight.class));
    }
}
