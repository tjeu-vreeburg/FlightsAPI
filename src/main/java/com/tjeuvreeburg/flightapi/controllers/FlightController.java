package com.tjeuvreeburg.flightapi.controllers;

import com.tjeuvreeburg.flightapi.entities.Flight;
import com.tjeuvreeburg.flightapi.responses.PaginatedResponse;
import com.tjeuvreeburg.flightapi.services.FlightService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody Flight flight) {
        return ResponseEntity.ok(flightService.save(flight));
    }

    @GetMapping
    public PaginatedResponse<Flight> getFlights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var flightsPage = flightService.findAll(pageable);
        return PaginatedResponse.from(flightsPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight, @PathVariable Long id) {
        return ResponseEntity.ok(flightService.update(id, flight));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(long id) {
        flightService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable long id) {
        return ResponseEntity.ok(flightService.getById(id));
    }

    @GetMapping("/airport/{id}")
    public ResponseEntity<List<Flight>> getFlightsWithAirport(@PathVariable long id) {
        return ResponseEntity.ok(flightService.findFlightsWithAirport(id));
    }
}