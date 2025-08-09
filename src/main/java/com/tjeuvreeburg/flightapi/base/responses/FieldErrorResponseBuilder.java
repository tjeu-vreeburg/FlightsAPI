package com.tjeuvreeburg.flightapi.base.responses;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

public class FieldErrorResponseBuilder {

    private final Map<String, String> errors = new HashMap<>();

    public static FieldErrorResponseBuilder newBuilder() {
        return new FieldErrorResponseBuilder();
    }

    public FieldErrorResponseBuilder addConstrainViolationException(ConstraintViolationException ex) {
        ex.getConstraintViolations().forEach(violation -> {
            var field = violation.getPropertyPath().toString();
            errors.put(field, violation.getMessage());
        });
        return this;
    }

    public FieldErrorResponseBuilder addMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return this;
    }

    public ResponseEntity<Map<String, String>> submit(HttpStatus httpStatus) {
        return new ResponseEntity<>(errors, httpStatus);
    }
}