package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.entities.GenericEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Generic service interface that defines common CRUD operations for entities.
 *
 * @param <T> The type of the entity, extending {@link GenericEntity}.
 * @param <K> The type of the specification used for filtering, extending {@link Specification}.
 */
public interface GenericService<T extends GenericEntity, K extends Specification<T>> {

    /**
     * Deletes an entity by its unique identifier.
     *
     * @param id The unique identifier of the entity to delete.
     */
    void delete(long id);

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id The unique identifier of the entity.
     * @return The entity corresponding to the given identifier.
     * @throws IllegalArgumentException if no entity is found for the given id.
     */
    T getById(long id);

    /**
     * Persists a new entity.
     *
     * @param t The entity to save.
     * @return The saved entity, including any auto-generated fields such as ID.
     */
    T save(T t);

    /**
     * Updates an existing entity with new values.
     *
     * @param id The unique identifier of the entity to update.
     * @param t  The entity data to update.
     * @return The updated entity.
     * @throws IllegalArgumentException if the entity with the given ID does not exist.
     */
    T update(long id, T t);

    /**
     * Retrieves all entities matching a given specification with pagination.
     *
     * <p><b>Default implementation:</b> Throws {@link UnsupportedOperationException}
     * unless overridden by the implementing service.</p>
     *
     * @param specification The filtering specification for the query.
     * @param pageable      The pagination information.
     * @return A paginated list of entities matching the specification.
     * @throws UnsupportedOperationException if the method is not implemented.
     */
    default Page<T> findAll(K specification, Pageable pageable) {
        throw new UnsupportedOperationException("Finding records via specifications is not supported");
    }

    /**
     * Retrieves all entities with pagination, without applying any specifications.
     *
     * <p><b>Default implementation:</b> Throws {@link UnsupportedOperationException}
     * unless overridden by the implementing service.</p>
     *
     * @param pageable The pagination information.
     * @return A paginated list of all entities.
     * @throws UnsupportedOperationException if the method is not implemented.
     */
    default Page<T> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Finding records without specifications is not supported");
    }
}