package com.tjeuvreeburg.flightapi.base.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public record ErrorResponse(String error, String message, LocalDateTime timeStamp) {

    public ResponseEntity<ErrorResponse> submit(HttpStatus httpStatus) {
        return new ResponseEntity<>(this, httpStatus);
    }
}