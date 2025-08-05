package com.tjeuvreeburg.flightapi.repositories;

import com.tjeuvreeburg.flightapi.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f JOIN FETCH f.origin JOIN FETCH f.destination WHERE f.id = :id")
    List<Flight> findFlightsWithAirport(@Param("id") Long id);
}