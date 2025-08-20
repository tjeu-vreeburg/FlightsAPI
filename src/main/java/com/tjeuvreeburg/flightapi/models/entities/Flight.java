package com.tjeuvreeburg.flightapi.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "flights")
public class Flight implements GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Airport origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Airport destination;

    public Flight() {

    }

    public Flight(Integer number, Airport origin, Airport destination) {
        this.number = number;
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @JsonProperty("originId")
    public Long getOriginId() {
        return origin != null ? origin.getId() : null;
    }

    public Airport getOrigin() {
        return origin;
    }

    @JsonProperty("originId")
    public void setOriginId(Long originId) {
        if (originId != null) {
            Airport airport = new Airport();
            airport.setId(originId);
            this.origin = airport;
        } else {
            this.origin = null;
        }
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    @JsonProperty("destinationId")
    public Long getDestinationId() {
        return destination != null ? destination.getId() : null;
    }

    public Airport getDestination() {
        return destination;
    }

    @JsonProperty("destinationId")
    public void setDestinationId(Long destinationId) {
        if (destinationId != null) {
            Airport airport = new Airport();
            airport.setId(destinationId);
            this.destination = airport;
        } else {
            this.destination = null;
        }
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }
}