package com.tjeuvreeburg.flightapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    private Flight flight;

    private String passenger;

    public Booking() {

    }

    public Long getId() {
        return id;
    }

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

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", flight=" + flight +
                ", passenger='" + passenger + '\'' +
                '}';
    }
}