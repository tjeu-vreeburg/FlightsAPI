package com.tjeuvreeburg.flightapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bookings")
public class Booking implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Flight is required")
    @JsonIgnore
    private Flight flight;

    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Cabin class is required")
    @Column(nullable = false)
    private String cabinClass;

    @NotBlank(message = "Seat is required")
    @Column(nullable = false)
    private String seat;


    public Booking() {

    }

    public Booking(Flight flight, String firstName, String lastName, String cabinClass, String seat) {
        this.flight = flight;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", flight=" + flight +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cabinClass='" + cabinClass + '\'' +
                ", seat='" + seat + '\'' +
                '}';
    }
}