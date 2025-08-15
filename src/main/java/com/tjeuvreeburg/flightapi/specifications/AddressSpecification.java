package com.tjeuvreeburg.flightapi.specifications;

import com.tjeuvreeburg.flightapi.entities.Address;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

public class AddressSpecification implements Specification<Address> {

    private final String street;
    private final String city;
    private final String country;
    private final String postalCode;

    public AddressSpecification(String street, String city, String country, String postalCode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Address> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if (street != null) {
            predicate = cb.and(predicate, cb.equal(root.get("street"), street));
        }

        if (city != null) {
            predicate = cb.and(predicate, cb.equal(root.get("city"), city));
        }

        if (country != null) {
            predicate = cb.and(predicate, cb.equal(root.get("country"), country));
        }

        if (postalCode != null) {
            predicate = cb.and(predicate, cb.equal(root.get("postalCode"), postalCode));
        }

        return predicate;
    }

    public static AddressSpecification filter(String street, String city, String country, String postalCode) {
        return new AddressSpecification(street, city, country, postalCode);
    }
}