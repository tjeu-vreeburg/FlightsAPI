package com.tjeuvreeburg.flightapi.handlers;

import com.tjeuvreeburg.flightapi.base.exceptions.BadRequestException;
import com.tjeuvreeburg.flightapi.base.exceptions.ConflictException;
import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.base.responses.ErrorResponse;
import com.tjeuvreeburg.flightapi.base.responses.FieldErrorResponseBuilder;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        var error = new ErrorResponse("InternalServerError", ex.getMessage(), LocalDateTime.now());
        return error.submit(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        var error = new ErrorResponse("NotFound", ex.getMessage(), LocalDateTime.now());
        return error.submit(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        var error = new ErrorResponse("BadRequest", ex.getMessage(), LocalDateTime.now());
        return error.submit(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(ConflictException ex) {
        var error = new ErrorResponse("Conflict", ex.getMessage(), LocalDateTime.now());
        return error.submit(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        return FieldErrorResponseBuilder.newBuilder()
                .addMethodArgumentNotValidExceptions(ex)
                .submit(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolations(ConstraintViolationException ex) {
        return FieldErrorResponseBuilder.newBuilder()
                .addConstrainViolationException(ex)
                .submit(HttpStatus.BAD_REQUEST);
    }
}