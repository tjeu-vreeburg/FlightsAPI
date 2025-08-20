package com.tjeuvreeburg.flightapi.functional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getFlightSuccess() throws Exception {
        mockMvc.perform(get("/api/flights/details/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.origin.id").value(1L))
                .andExpect(jsonPath("$.destination.id").value(2L));
    }

    @Test
    public void getFlightFailure() throws Exception {
        mockMvc.perform(get("/api/flights/details/{id}", 2L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Could not find flight with id: 2"));
    }

    @Test
    public void cancelFlightFailure() throws Exception {
        mockMvc.perform(delete("/api/flights/delete/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Cannot delete flight with existing bookings."));
    }

    @Test
    public void searchFlightsSuccess() throws Exception {
        var endpoint = "/api/flights/search?origin={city}&destination={country}";
        mockMvc.perform(get(endpoint, "Auckland", "Melbourne")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].number").value(10));
    }
}