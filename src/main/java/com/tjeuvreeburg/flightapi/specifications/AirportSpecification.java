package com.tjeuvreeburg.flightapi.specifications;

import com.tjeuvreeburg.flightapi.entities.Airport;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

public class AirportSpecification implements Specification<Airport> {

    private final String city;
    private final String country;

    public AirportSpecification(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Airport> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if (city != null) {
            predicate = cb.and(predicate, cb.equal(root.get("city"), city));
        }

        if (country != null) {
            predicate = cb.and(predicate, cb.equal(root.get("country"), country));
        }

        return predicate;
    }

    public static AirportSpecification filter(String city, String country) {
        return new AirportSpecification(city, country);
    }
}