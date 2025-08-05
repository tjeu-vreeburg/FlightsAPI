package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.entities.GenericEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface GenericService<T extends GenericEntity, K extends Specification<T>> {

    void delete(long id);

    T getById(long id);

    T save(T t);

    T update(long id, T t);

    default Page<T> findAll(K specification, Pageable pageable) {
        throw new UnsupportedOperationException("Finding records via specifications is not supported");
    }

    default Page<T> findAll(Pageable pageable) {
        throw new UnsupportedOperationException("Finding records without specifications is not supported");
    }
}