package com.tjeuvreeburg.flightapi.controllers;

import com.tjeuvreeburg.flightapi.entities.Booking;
import com.tjeuvreeburg.flightapi.base.responses.PaginatedResponse;
import com.tjeuvreeburg.flightapi.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.save(booking));
    }

    @GetMapping
    public PaginatedResponse<Booking> getBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var bookingPage = bookingService.findAll(pageable);
        return PaginatedResponse.from(bookingPage);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Booking> updateBooking(@RequestBody Booking booking, @PathVariable Long id) {
        return ResponseEntity.ok(bookingService.update(id, booking));
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Boolean> cancelBooking(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Booking> getBookingDetails(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getById(id));
    }
}