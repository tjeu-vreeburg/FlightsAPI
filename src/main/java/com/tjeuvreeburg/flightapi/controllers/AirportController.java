package com.tjeuvreeburg.flightapi.controllers;

import com.tjeuvreeburg.flightapi.entities.Airport;
import com.tjeuvreeburg.flightapi.base.responses.PaginatedResponse;
import com.tjeuvreeburg.flightapi.services.AirportService;
import com.tjeuvreeburg.flightapi.specifications.AirportSpecification;
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

    @GetMapping("/search")
    public PaginatedResponse<Airport> searchAirports(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var airportSpecification = AirportSpecification.filter(city, country);
        var airportPage = airportService.findAll(airportSpecification, pageable);
        return PaginatedResponse.from(airportPage);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Airport> updateAirport(@RequestBody Airport airport, @PathVariable Long id) {
        return ResponseEntity.ok(airportService.update(id, airport));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteAirport(@PathVariable Long id) {
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Airport> getAirportDetails(@PathVariable Long id) {
        return ResponseEntity.ok(airportService.getById(id));
    }
}