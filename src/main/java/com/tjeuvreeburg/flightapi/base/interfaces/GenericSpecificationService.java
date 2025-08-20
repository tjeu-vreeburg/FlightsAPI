package com.tjeuvreeburg.flightapi.base.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * GenericSpecificationService is an extension of {@link GenericService} that
 * adds support for querying entities using {@link Specification} criteria
 * along with pagination.
 *
 * <p>This interface is intended for services that require advanced filtering
 * capabilities beyond basic CRUD operations.</p>
 *
 * @param <E> the type of entity managed by the service; must extend {@link GenericEntity}
 * @param <D> the type of DTO returned by the service methods
 */
public interface GenericSpecificationService<E extends GenericEntity, D> extends GenericService<D> {

    /**
     * Retrieves all entities that match the given specification, with pagination support.
     *
     * <p>The default implementation throws {@link UnsupportedOperationException} and
     * should be overridden by concrete service implementations that need this functionality.</p>
     *
     * @param specification the filtering criteria to apply; may be {@code null} for no filtering
     * @param pageable      the pagination information, including page number, size, and sort
     * @return a {@link Page} containing the entities that match the specification
     * @throws UnsupportedOperationException if this method is not implemented in the concrete service
     */
    default Page<D> findAll(Specification<E> specification, Pageable pageable) {
        throw new UnsupportedOperationException("Finding records via specifications is not supported");
    }
}
