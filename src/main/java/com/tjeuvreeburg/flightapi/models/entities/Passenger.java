package com.tjeuvreeburg.flightapi.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "passengers")
public class Passenger implements GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Address address;

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonProperty("addressId")
    public Long getAddressId() {
        return address != null ? address.getId() : null;
    }

    @JsonProperty("addressId")
    public void setAddressId(Long addressId) {
        if (addressId != null) {
            Address address = new Address();
            address.setId(addressId);
            this.address = address;
        } else {
            this.address = null;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}