package com.tjeuvreeburg.flightapi.entities;

import com.tjeuvreeburg.flightapi.base.interfaces.GenericEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "airports")
public class Airport implements GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Country is required")
    @Column(nullable = false)
    private String country;

    @NotBlank(message = "IATA Code is required")
    @Size(min = 3, max = 3, message = "IATA must be exactly 3 characters")
    @Column(nullable = false, length = 3)
    private String iata;

    @NotBlank(message = "ICAO Code is required")
    @Size(min = 4, max = 4, message = "ICAO must be exactly 4 characters")
    @Column(nullable = false, length = 4)
    private String icao;

    public Airport() {

    }

    public Airport(String name, String city, String country, String iata, String icao) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.iata = iata;
        this.icao = icao;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", iata='" + iata + '\'' +
                ", icao='" + icao + '\'' +
                '}';
    }
}
