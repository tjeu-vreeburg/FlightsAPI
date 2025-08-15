package com.tjeuvreeburg.flightapi.base.abstraction;

import com.tjeuvreeburg.flightapi.base.interfaces.GenericController;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericEntity;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AbstractController is a generic REST controller providing basic CRUD operations
 * for entities extending {@link GenericEntity}. This class delegates the actual
 * business logic to a {@link GenericService}.
 *
 * @param <T> the type of entity managed by this controller
 */
public abstract class AbstractController<T extends GenericEntity> implements GenericController<T> {

    /**
     * The generic service that handles business logic for the entity.
     */
    protected final GenericService<T> genericService;

    /**
     * Constructs a new AbstractController with the specified generic service.
     *
     * @param genericService the service responsible for entity operations
     */
    protected AbstractController(GenericService<T> genericService) {
        this.genericService = genericService;
    }

    /**
     * Creates a new entity.
     *
     * @param t the entity to create; must be valid according to Bean Validation
     * @return the created entity wrapped in a {@link ResponseEntity} with HTTP 200
     */
    @PostMapping("/create")
    @Override
    public ResponseEntity<T> create(@Valid @RequestBody T t) {
        return ResponseEntity.ok(genericService.save(t));
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id the ID of the entity to delete
     * @return an empty {@link ResponseEntity} with HTTP 204 (No Content)
     */
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<T> delete(@PathVariable long id) {
        genericService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates an existing entity by its ID.
     *
     * @param id the ID of the entity to update
     * @param t  the updated entity; must be valid according to Bean Validation
     * @return the updated entity wrapped in a {@link ResponseEntity} with HTTP 200
     */
    @PostMapping("/update/{id}")
    @Override
    public ResponseEntity<T> update(@PathVariable long id, @Valid @RequestBody T t) {
        return ResponseEntity.ok(genericService.update(id, t));
    }

    /**
     * Retrieves the details of an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return the entity wrapped in a {@link ResponseEntity} with HTTP 200
     */
    @GetMapping("/details/{id}")
    @Override
    public ResponseEntity<T> details(@PathVariable long id) {
        return ResponseEntity.ok(genericService.getById(id));
    }
}