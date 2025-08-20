package com.tjeuvreeburg.flightapi.repositories;

import com.tjeuvreeburg.flightapi.models.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>, JpaSpecificationExecutor<Flight> {

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Flight f WHERE f.origin.id = :originId")
    boolean existsByOriginId(@Param("originId") Long originId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Flight f WHERE f.destination.id = :destinationId")
    boolean existsByDestinationId(@Param("destinationId") Long destinationId);
}