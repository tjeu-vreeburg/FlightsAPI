package com.tjeuvreeburg.flightapi.base.interfaces;

/**
 * Represents a generic entity with a unique identifier.
 *
 * <p>This interface provides a standard contract for entities to expose
 * their primary key (ID) and allows setting or retrieving it.
 * It is typically used for generic service or repository layers where
 * operations need to work with entities in a type-safe manner.</p>
 */
public interface GenericEntity {

    /**
     * Retrieves the unique identifier of the entity.
     *
     * @return The ID of the entity.
     */
    Long getId();

    /**
     * Sets the unique identifier of the entity.
     *
     * @param id The ID to assign to the entity.
     */
    void setId(Long id);
}