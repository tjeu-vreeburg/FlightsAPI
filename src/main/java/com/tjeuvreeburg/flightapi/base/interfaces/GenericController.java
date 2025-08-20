package com.tjeuvreeburg.flightapi.base.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

/**
 * GenericController defines a generic REST API contract for CRUD operations
 * on DTOs extending {@link GenericDto}.
 *
 * @param <T> the type of DTO this controller manages
 */
public interface GenericController<T extends GenericDto> {

    /**
     * Creates a new resource.
     *
     * @param t the DTO representing the resource to create; must be valid according to Bean Validation
     * @return a {@link ResponseEntity} containing the created resource
     */
    ResponseEntity<T> create(@Valid T t);

    /**
     * Deletes a resource by its unique identifier.
     *
     * @param id the ID of the resource to delete
     * @return a {@link ResponseEntity} indicating the outcome of the delete operation
     */
    ResponseEntity<T> delete(long id);

    /**
     * Updates an existing resource identified by its ID.
     *
     * @param id the ID of the resource to update
     * @param t  the DTO containing updated data; must be valid according to Bean Validation
     * @return a {@link ResponseEntity} containing the updated resource
     */
    ResponseEntity<T> update(long id, @Valid T t);

    /**
     * Retrieves the details of a resource by its ID.
     *
     * @param id the ID of the resource to retrieve
     * @return a {@link ResponseEntity} containing the requested resource
     */
    ResponseEntity<T> details(long id);
}
