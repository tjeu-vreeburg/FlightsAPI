package com.tjeuvreeburg.flightapi.specifications;

import com.tjeuvreeburg.flightapi.entities.Flight;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

public class FlightSpecification implements Specification<Flight> {

    private final String originCity;
    private final String destinationCity;

    public FlightSpecification(String originCity, String destinationCity) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Flight> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if (originCity != null) {
            var originJoin = root.join("origin", JoinType.INNER);
            predicate = cb.and(predicate, cb.equal(originJoin.get("city"), originCity));
        }

        if (destinationCity != null) {
            var destinationJoin = root.join("destination", JoinType.INNER);
            predicate = cb.and(predicate, cb.equal(destinationJoin.get("city"), destinationCity));
        }

        return predicate;
    }

    public static FlightSpecification filter(String originCity, String destinationCity) {
        return new FlightSpecification(originCity, destinationCity);
    }
}