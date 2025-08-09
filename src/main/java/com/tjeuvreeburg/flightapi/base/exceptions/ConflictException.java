package com.tjeuvreeburg.flightapi.base.exceptions;

public class ConflictException extends RuntimeException {

    public ConflictException(String action, String type, String entity) {
        super("Cannot " + action + " " + type + " with existing " + entity + ".");
    }
}