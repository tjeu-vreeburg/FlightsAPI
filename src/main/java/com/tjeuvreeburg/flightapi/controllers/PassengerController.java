package com.tjeuvreeburg.flightapi.controllers;

import com.tjeuvreeburg.flightapi.base.abstraction.AbstractController;
import com.tjeuvreeburg.flightapi.base.responses.PaginatedResponse;
import com.tjeuvreeburg.flightapi.models.dto.PassengerDto;
import com.tjeuvreeburg.flightapi.services.PassengerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/passengers")
public class PassengerController extends AbstractController<PassengerDto> {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        super(passengerService);
        this.passengerService = passengerService;
    }

    @GetMapping
    public PaginatedResponse<PassengerDto> getPassengers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var passengerPage = passengerService.findAll(pageable);
        return PaginatedResponse.from(passengerPage);
    }
}