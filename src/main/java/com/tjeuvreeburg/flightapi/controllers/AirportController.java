package com.tjeuvreeburg.flightapi.controllers;

import com.tjeuvreeburg.flightapi.entities.Airport;
import com.tjeuvreeburg.flightapi.responses.PaginatedResponse;
import com.tjeuvreeburg.flightapi.services.AirportService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping("/create")
    public ResponseEntity<Airport> createAirport(@Valid @RequestBody Airport airport) {
        return ResponseEntity.ok(airportService.save(airport));
    }

    @GetMapping
    public PaginatedResponse<Airport> getAirports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var airportPage = airportService.findAll(pageable);
        return PaginatedResponse.from(airportPage);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Airport> updateAirport(@RequestBody Airport airport, @PathVariable Long id) {
        return ResponseEntity.ok(airportService.update(id, airport));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteAirport(@PathVariable long id) {
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Airport> getAirportDetails(@PathVariable long id) {
        return ResponseEntity.ok(airportService.getById(id));
    }
}