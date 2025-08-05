package com.tjeuvreeburg.flightapi.exceptions;

public record ErrorResponse(String error, String message, String timeStamp, int status) {

}