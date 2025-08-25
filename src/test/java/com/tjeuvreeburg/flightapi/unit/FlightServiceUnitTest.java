package com.tjeuvreeburg.flightapi.unit;

import com.tjeuvreeburg.flightapi.base.exceptions.ConflictException;
import com.tjeuvreeburg.flightapi.models.dto.AirportDto;
import com.tjeuvreeburg.flightapi.models.dto.FlightDto;
import com.tjeuvreeburg.flightapi.models.entities.Airport;
import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.models.entities.Flight;
import com.tjeuvreeburg.flightapi.models.mappers.FlightMapper;
import com.tjeuvreeburg.flightapi.repositories.AirportRepository;
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
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceUnitTest {

    @Mock
    private AirportRepository airportRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightMapper flightMapper;

    @InjectMocks
    private FlightService flightService;

    private Airport origin;
    private Airport destination;
    private Flight flight;

    private AirportDto originDto;
    private AirportDto destinationDto;
    private FlightDto flightDto;

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
        when(flightMapper.toDto(flight)).thenReturn(flightDto);

        var result = flightService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(originDto.id(), result.origin().id());
        assertEquals(destinationDto.id(), result.destination().id());
        verify(flightRepository, times(1)).findById(1L);
    }

    @Test
    public void verifyFlightUpdated() {
        var originEntity = DataHelper.createAirportEntity(1L);
        var destinationEntity = DataHelper.createAirportEntity(2L);

        var updateFlightDto = DataHelper.createFlightDto(1L, originDto, destinationDto);
        var updateFlight = DataHelper.createFlightEntity(1L, originEntity, destinationEntity);

        when(airportRepository.findById(1L)).thenReturn(Optional.of(originEntity));
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightRepository.saveAndFlush(any(Flight.class))).thenReturn(updateFlight);
        when(flightMapper.toDto(updateFlight)).thenReturn(updateFlightDto);

        var result = flightService.update(1L, updateFlightDto);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(1L, result.origin().id());
        assertEquals(2L, result.destination().id());

        verify(flightRepository).saveAndFlush(any(Flight.class));
    }

    @Test
    public void verifyFlightSaved() {
        var newFlightEntity = DataHelper.createFlightEntity(0L, origin, destination);

        when(airportRepository.findById(1L)).thenReturn(Optional.of(origin));
        when(airportRepository.findById(2L)).thenReturn(Optional.of(destination));

        when(flightMapper.toEntity(flightDto)).thenReturn(newFlightEntity);
        when(flightRepository.saveAndFlush(any(Flight.class))).thenReturn(flight);
        when(flightMapper.toDto(flight)).thenReturn(flightDto);

        var result = flightService.save(flightDto);

        assertNotNull(result);
        assertEquals(1L, result.id());

        verify(flightMapper).toEntity(flightDto);
        verify(flightRepository).saveAndFlush(any(Flight.class));
        verify(flightMapper).toDto(flight);
    }

    @Test
    public void verifyFlightsHaveBeenFound() {
        var page = new PageImpl<>(List.of(flight));
        var flightSpecification = new FlightSpecification(origin.getCity(), destination.getCity());
        when(flightRepository.findAll(any(FlightSpecification.class), any(PageRequest.class))).thenReturn(page);
        when(flightMapper.toDto(flight)).thenReturn(flightDto);

        var result = flightService.findAll(flightSpecification, Pageable.ofSize(1));

        assertEquals(1, result.getTotalElements());
        assertEquals(0, result.getNumber());
        verify(flightRepository, times(1)).findAll(any(FlightSpecification.class), any(PageRequest.class));
    }

    @Test
    public void verifyFlightCannotBeDeletedWhenBookingsExists() {
        when(bookingRepository.existsByFlightId(1L)).thenReturn(true);

        var ex = assertThrows(ConflictException.class, () -> flightService.delete(1L));
        assertTrue(ex.getMessage().contains("Cannot delete flight with existing bookings."));

        verify(flightRepository, times(0)).deleteById(1L);
    }

    @Test
    public void verifyFlightCanBeDeleted() {
        when(bookingRepository.existsByFlightId(1L)).thenReturn(false);

        flightService.delete(1L);

        verify(flightRepository, times(1)).deleteById(1L);
    }

    @Test
    public void verifyResourceNotFoundException() {
        when(flightRepository.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> flightService.update(99L, flightDto));
        assertTrue(ex.getMessage().contains("Could not find flight with id: 99"));

        verify(flightRepository).findById(99L);
        verify(flightRepository, never()).saveAndFlush(any(Flight.class));
    }

    @Test
    public void verifySaveThrowsExceptionWhenAirportNotFound() {
        var unknownAirportEntity = DataHelper.createAirportEntity(99L);
        var newFlightEntity = DataHelper.createFlightEntity(1L, unknownAirportEntity, destination);

        when(flightMapper.toEntity(flightDto)).thenReturn(newFlightEntity);
        when(airportRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> flightService.save(flightDto));
        assertTrue(ex.getMessage().contains("Could not find origin airport with id: 99"));

        verify(airportRepository).findById(99L);
        verify(flightRepository, never()).saveAndFlush(any(Flight.class));
    }
}