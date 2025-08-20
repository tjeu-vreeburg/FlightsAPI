package com.tjeuvreeburg.flightapi.controllers;

import com.tjeuvreeburg.flightapi.base.abstraction.AbstractController;
import com.tjeuvreeburg.flightapi.models.dto.BookingDto;
import com.tjeuvreeburg.flightapi.base.responses.PaginatedResponse;
import com.tjeuvreeburg.flightapi.services.BookingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/bookings")
public class BookingController extends AbstractController<BookingDto> {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        super(bookingService);
        this.bookingService = bookingService;
    }

    @GetMapping
    public PaginatedResponse<BookingDto> getBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var bookingPage = bookingService.findAll(pageable);
        return PaginatedResponse.from(bookingPage);
    }
}