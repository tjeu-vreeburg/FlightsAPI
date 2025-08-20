package com.tjeuvreeburg.flightapi.models.entities;

import com.tjeuvreeburg.flightapi.base.interfaces.GenericEntity;
import com.tjeuvreeburg.flightapi.models.dto.AddressDto;
import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address implements GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String postalCode;

    public Address() {

    }

    public Address(AddressDto addressDto) {
        this.id = addressDto.id();
        this.street = addressDto.street();
        this.city = addressDto.city();
        this.country = addressDto.country();
        this.postalCode = addressDto.postalCode();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}