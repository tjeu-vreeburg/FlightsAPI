package com.tjeuvreeburg.flightapi.controllers;

import com.tjeuvreeburg.flightapi.entities.Flight;
import com.tjeuvreeburg.flightapi.responses.PaginatedResponse;
import com.tjeuvreeburg.flightapi.services.FlightService;
import com.tjeuvreeburg.flightapi.specifications.FlightSpecification;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/create")
    public ResponseEntity<Flight> createFlight(@Valid @RequestBody Flight flight) {
        return ResponseEntity.ok(flightService.save(flight));
    }

    @GetMapping("/search")
    public PaginatedResponse<Flight> searchFlights(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var flightSpecification = new FlightSpecification(origin, destination);
        var flightsPage = flightService.findAll(flightSpecification, pageable);

        return PaginatedResponse.from(flightsPage);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight, @PathVariable Long id) {
        return ResponseEntity.ok(flightService.update(id, flight));
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Boolean> cancelFlight(long id) {
        flightService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Flight> getFlightDetails(@PathVariable long id) {
        return ResponseEntity.ok(flightService.getById(id));
    }
}