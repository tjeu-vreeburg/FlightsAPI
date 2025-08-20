package com.tjeuvreeburg.flightapi.functional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBookingSuccess() throws Exception {
        mockMvc.perform(get("/api/bookings/details/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.passenger.id").value(1L))
                .andExpect(jsonPath("$.flight.id").value(1L));
    }

    @Test
    public void getBookingFailure() throws Exception {
        mockMvc.perform(get("/api/bookings/details/{id}", 3L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Could not find booking with id: 3"));
    }
}

