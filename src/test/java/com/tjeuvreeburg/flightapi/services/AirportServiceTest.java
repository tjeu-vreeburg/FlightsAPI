package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.entities.Airport;
import com.tjeuvreeburg.flightapi.repositories.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportService airportService;

    @Test
    void verifyAirportExists() {
        Airport airport = new Airport();
        airport.setId(1L);

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));

        Airport result = airportService.getById(1L);

        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
        verify(airportRepository, times(1)).findById(1L);
    }
}