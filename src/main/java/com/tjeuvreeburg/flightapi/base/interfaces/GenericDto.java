package com.tjeuvreeburg.flightapi.base.interfaces;

/**
 * Marker interface for Data Transfer Objects (DTOs) that have an identifier.
 * <p>
 * Implementing classes represent a DTO with a unique {@code id} that can be used
 * to identify the corresponding entity in the system.
 */
public interface GenericDto {

    /**
     * Returns the unique identifier of the DTO.
     *
     * @return the ID of the DTO, may be {@code null} if not yet persisted
     */
    Long id();
}