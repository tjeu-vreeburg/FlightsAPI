package com.tjeuvreeburg.flightapi.base.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * GenericSpecificationService is an extension of {@link GenericService} that
 * provides support for querying entities using {@link Specification} criteria
 * along with pagination.
 *
 * <p>This interface is intended for services that require advanced filtering
 * capabilities beyond simple CRUD operations.</p>
 *
 * @param <T> the type of entity managed by the service
 * @param <K> the type of {@link Specification} used for filtering entities
 */
public interface GenericSpecificationService<T extends GenericEntity, K extends Specification<T>>
        extends GenericService<T> {

    /**
     * Retrieves all entities matching the given specification with pagination support.
     *
     * <p><b>Default implementation:</b> Throws {@link UnsupportedOperationException}
     * unless the implementing service overrides this method.</p>
     *
     * @param specification the filtering criteria to apply to the query
     * @param pageable      the pagination information, including page number, size, and sort
     * @return a {@link Page} containing the entities that match the specification
     * @throws UnsupportedOperationException if the method is not implemented in the concrete service
     */
    default Page<T> findAll(K specification, Pageable pageable) {
        throw new UnsupportedOperationException("Finding records via specifications is not supported");
    }
}