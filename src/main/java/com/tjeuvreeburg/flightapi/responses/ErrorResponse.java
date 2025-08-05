package com.tjeuvreeburg.flightapi.responses;

import java.time.LocalDateTime;

public record ErrorResponse(
        String error,
        String message,
        LocalDateTime timeStamp
) {

}