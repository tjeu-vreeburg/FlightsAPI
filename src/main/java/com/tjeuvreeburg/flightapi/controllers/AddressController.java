package com.tjeuvreeburg.flightapi.controllers;

import com.tjeuvreeburg.flightapi.base.abstraction.AbstractController;
import com.tjeuvreeburg.flightapi.base.responses.PaginatedResponse;
import com.tjeuvreeburg.flightapi.entities.Address;
import com.tjeuvreeburg.flightapi.services.AddressService;
import com.tjeuvreeburg.flightapi.specifications.AddressSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/addresses")
public class AddressController extends AbstractController<Address> {

    private final AddressService addressService;

    protected AddressController(AddressService addressService) {
        super(addressService);
        this.addressService = addressService;
    }

    @GetMapping("/search")
    public PaginatedResponse<Address> searchAddresses(
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String postalCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var addressSpecification = AddressSpecification.filter(street, city, country, postalCode);
        var addressPage = addressService.findAll(addressSpecification, pageable);
        return PaginatedResponse.from(addressPage);
    }
}