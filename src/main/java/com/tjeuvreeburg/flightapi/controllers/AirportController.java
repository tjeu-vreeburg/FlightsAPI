package com.tjeuvreeburg.flightapi.controllers;

import com.tjeuvreeburg.flightapi.model.Airport;
import com.tjeuvreeburg.flightapi.services.AirportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }


    @PostMapping("/")
    public ResponseEntity<Airport> saveAirport(@RequestBody Airport airport) {
        return ResponseEntity.ok(airportService.save(airport));
    }

    @GetMapping("/")
    public ResponseEntity<List<Airport>> getAirports() {
        return ResponseEntity.ok(airportService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirport(@PathVariable long id) {
        return ResponseEntity.ok(airportService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAirport(@PathVariable long id) {
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}