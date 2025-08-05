package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.entities.IEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService<T extends IEntity> {

    void delete(long id);

    T getById(long id);

    T save(T t);

    T update(long id, T t);

    Page<T> findAll(Pageable pageable);
}