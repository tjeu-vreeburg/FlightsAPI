package com.tjeuvreeburg.flightapi.utilities;

import com.tjeuvreeburg.flightapi.entities.Airport;
import com.tjeuvreeburg.flightapi.entities.Booking;
import com.tjeuvreeburg.flightapi.entities.Flight;

public class DataHelper {

    public static Airport createAirport(long id) {
        Airport airport = new Airport();
        airport.setId(id);
        airport.setName("Test Airport " + id);
        airport.setCity("Test City " + id);
        airport.setCountry("Test Country " + id);
        airport.setIata("12" + id);
        airport.setIcao("123" + id);
        return airport;
    }

    public static Flight createFlight(long id, Airport origin, Airport destination) {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setNumber((int) (1 + id));
        flight.setOrigin(origin);
        flight.setDestination(destination);
        return flight;
    }

    public static Booking createBooking(long id, Flight flight) {
        Booking booking = new Booking();
        booking.setId(id);
        booking.setFirstName("Test First Name " + id);
        booking.setLastName("Test Last Name " + id);
        booking.setCabinClass("Test Cabin Class " + id);
        booking.setSeat("Test Seat " + id);
        booking.setFlight(flight);
        return booking;
    }
}