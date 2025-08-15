package com.tjeuvreeburg.flightapi.base.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

/**
 * GenericController defines a generic REST API contract for CRUD operations
 * on entities extending {@link GenericEntity}.
 *
 * @param <T> the type of entity this controller manages
 */
public interface GenericController<T extends GenericEntity> {

    /**
     * Creates a new entity.
     *
     * @param t the entity to create; must be valid according to Bean Validation
     * @return a {@link ResponseEntity} containing the created entity
     */
    ResponseEntity<T> create(@Valid T t);

    /**
     * Deletes an entity by its unique identifier.
     *
     * @param id the ID of the entity to delete
     * @return a {@link ResponseEntity} indicating the result of the delete operation
     */
    ResponseEntity<T> delete(long id);

    /**
     * Updates an existing entity identified by its ID.
     *
     * @param id the ID of the entity to update
     * @param t  the updated entity; must be valid according to Bean Validation
     * @return a {@link ResponseEntity} containing the updated entity
     */
    ResponseEntity<T> update(long id, @Valid T t);

    /**
     * Retrieves the details of an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return a {@link ResponseEntity} containing the requested entity
     */
    ResponseEntity<T> details(long id);
}
