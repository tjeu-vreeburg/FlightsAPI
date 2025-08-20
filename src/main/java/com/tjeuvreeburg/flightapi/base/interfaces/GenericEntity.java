package com.tjeuvreeburg.flightapi.base.interfaces;

/**
 * Represents a generic entity with a unique identifier.
 * <p>
 * Implementing classes are expected to provide access to their unique ID, which
 * can be used for persistence, equality checks, and other entity-related operations.
 */
public interface GenericEntity {

    /**
     * Returns the unique identifier of the entity.
     *
     * @return the ID of the entity, may be {@code null} if the entity is not yet persisted
     */
    Long getId();

    /**
     * Sets the unique identifier of the entity.
     *
     * @param id the ID to assign to the entity
     */
    void setId(Long id);
}
