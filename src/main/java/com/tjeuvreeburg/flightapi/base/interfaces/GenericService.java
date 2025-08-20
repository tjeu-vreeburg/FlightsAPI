package com.tjeuvreeburg.flightapi.base.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Generic service interface defining common CRUD operations for entities.
 *
 * @param <D> the type of entity managed by this service, typically a DTO or entity class
 */
public interface GenericService<D> {

    /**
     * Deletes an entity by its unique identifier.
     *
     * @param id the unique identifier of the entity to delete
     * @throws IllegalArgumentException if no entity exists with the given ID
     */
    void delete(long id);

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id the unique identifier of the entity
     * @return the entity corresponding to the given identifier
     * @throws IllegalArgumentException if no entity exists with the given ID
     */
    D getById(long id);

    /**
     * Persists a new entity.
     *
     * @param t the entity to save; must not be {@code null}
     * @return the saved entity, including any auto-generated fields such as ID
     */
    D save(D t);

    /**
     * Updates an existing entity identified by its unique ID.
     *
     * @param id the ID of the entity to update
     * @param t  the entity data to apply
     * @return the updated entity
     * @throws IllegalArgumentException if no entity exists with the given ID
     */
    D update(long id, D t);

    /**
     * Retrieves all entities with pagination.
     * <p>
     * The default implementation throws {@link UnsupportedOperationException} and
     * should be overridden if a paginated listing without additional filtering is required.
     *
     * @param pageable the pagination information
     * @return a paginated list of all entities
     * @throws UnsupportedOperationException if the method is not implemented
     */
    default Page<D> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Finding records without specifications is not supported");
    }
}
