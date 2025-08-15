package com.tjeuvreeburg.flightapi.controllers;

import com.tjeuvreeburg.flightapi.base.abstraction.AbstractController;
import com.tjeuvreeburg.flightapi.entities.Flight;
import com.tjeuvreeburg.flightapi.base.responses.PaginatedResponse;
import com.tjeuvreeburg.flightapi.services.FlightService;
import com.tjeuvreeburg.flightapi.specifications.FlightSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/flights")
public class FlightController extends AbstractController<Flight> {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        super(flightService);
        this.flightService = flightService;
    }

    @GetMapping("/search")
    public PaginatedResponse<Flight> searchFlights(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var flightSpecification = FlightSpecification.filter(origin, destination);
        var flightsPage = flightService.findAll(flightSpecification, pageable);

        return PaginatedResponse.from(flightsPage);
    }
}