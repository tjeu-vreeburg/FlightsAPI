package com.tjeuvreeburg.flightapi.controllers;

import com.tjeuvreeburg.flightapi.base.abstraction.AbstractController;
import com.tjeuvreeburg.flightapi.models.dto.AirportDto;
import com.tjeuvreeburg.flightapi.base.responses.PaginatedResponse;
import com.tjeuvreeburg.flightapi.services.AirportService;
import com.tjeuvreeburg.flightapi.specifications.AirportSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/airports")
public class AirportController extends AbstractController<AirportDto> {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        super(airportService);
        this.airportService = airportService;
    }

    @GetMapping("/search")
    public PaginatedResponse<AirportDto> searchAirports(
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
}