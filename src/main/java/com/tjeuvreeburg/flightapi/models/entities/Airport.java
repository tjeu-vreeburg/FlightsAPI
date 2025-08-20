package com.tjeuvreeburg.flightapi.models.entities;

import com.tjeuvreeburg.flightapi.base.interfaces.GenericEntity;
import com.tjeuvreeburg.flightapi.models.dto.AirportDto;
import jakarta.persistence.*;

@Entity
@Table(name = "airports")
public class Airport implements GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false, length = 3)
    private String iata;

    @Column(nullable = false, length = 4)
    private String icao;

    public Airport() {

    }

    public Airport(AirportDto airportDto) {
        this.id = airportDto.id();
        this.name = airportDto.name();
        this.city = airportDto.city();
        this.country = airportDto.country();
        this.iata = airportDto.iata();
        this.icao = airportDto.icao();
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
}