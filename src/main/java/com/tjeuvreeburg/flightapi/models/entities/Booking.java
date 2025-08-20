package com.tjeuvreeburg.flightapi.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking implements GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Passenger passenger;

    @Column(nullable = false)
    private String cabinClass;

    @Column(nullable = false)
    private String seat;


    public Booking() {

    }

    public Booking(Flight flight, Passenger passenger, String cabinClass, String seat) {
        this.flight = flight;
        this.passenger = passenger;
        this.cabinClass = cabinClass;
        this.seat = seat;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("flightId")
    public Long getFlightId() {
        return flight != null ? flight.getId() : null;
    }

    public Flight getFlight() {
        return flight;
    }

    @JsonProperty("flightId")
    public void setFlightId(Long flightId) {
        if (flightId != null) {
            Flight flight = new Flight();
            flight.setId(flightId);
            this.flight = flight;
        } else {
            this.flight = null;
        }
    }

    @JsonProperty("passengerId")
    public Long getPassengerId() {
        return passenger != null ? passenger.getId() : null;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    @JsonProperty("passengerId")
    public void setPassengerId(Long passengerId) {
        if (passengerId != null) {
            Passenger passenger = new Passenger();
            passenger.setId(passengerId);
            this.passenger = passenger;
        } else {
            this.passenger = null;
        }
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}