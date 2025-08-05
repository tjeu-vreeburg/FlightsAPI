package com.tjeuvreeburg.flightapi.functional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AirportControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAirportSuccess() throws Exception {
        mockMvc.perform(get("/api/airports/details/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.city").value("Auckland"));
    }

    @Test
    public void getAirportFailure() throws Exception {
        mockMvc.perform(get("/api/airports/details/{id}", 3L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Could not find airport with id: 3"));
    }

    @Test
    public void deleteAirportFailure() throws Exception {
        mockMvc.perform(delete("/api/airports/delete/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Cannot delete airport with existing flights."));
    }
}

