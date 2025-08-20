package com.tjeuvreeburg.flightapi.utilities;

import com.tjeuvreeburg.flightapi.models.dto.AddressDto;
import com.tjeuvreeburg.flightapi.models.dto.AirportDto;
import com.tjeuvreeburg.flightapi.models.dto.FlightDto;
import com.tjeuvreeburg.flightapi.models.dto.BookingDto;
import com.tjeuvreeburg.flightapi.models.dto.PassengerDto;
import com.tjeuvreeburg.flightapi.models.entities.Airport;
import com.tjeuvreeburg.flightapi.models.entities.Flight;
import com.tjeuvreeburg.flightapi.models.entities.Booking;
import com.tjeuvreeburg.flightapi.models.entities.Passenger;
import com.tjeuvreeburg.flightapi.models.entities.Address;

import java.time.LocalDate;

public class DataHelper {

    public static Address createAddressEntity(long id) {
        var address = new Address();
        address.setId(id);
        address.setStreet("Street " + id);
        address.setCity("City " + id);
        address.setCountry("Country " + id);
        address.setPostalCode("100" + id);
        return address;
    }

    public static AddressDto createAddressDto(long id) {
        var address = createAddressEntity(id);
        return new AddressDto(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostalCode()
        );
    }

    public static Airport createAirportEntity(long id) {
        var airport = new Airport();
        airport.setId(id);
        airport.setName("Test Airport " + id);
        airport.setCity("Test City " + id);
        airport.setCountry("Test Country " + id);
        airport.setIata("12" + id);
        airport.setIcao("123" + id);
        return airport;
    }

    public static AirportDto createAirportDto(long id) {
        var airport = createAirportEntity(id);
        return new AirportDto(
                airport.getId(),
                airport.getName(),
                airport.getCity(),
                airport.getCountry(),
                airport.getIata(),
                airport.getIcao()
        );
    }

    public static Flight createFlightEntity(long id, Airport origin, Airport destination) {
        var flight = new Flight();
        flight.setId(id);
        flight.setNumber((int) (10 + id));
        flight.setOrigin(origin);
        flight.setDestination(destination);
        return flight;
    }

    public static FlightDto createFlightDto(long id, AirportDto originDto, AirportDto destinationDto) {
        return new FlightDto(
                id,
                (int) (10 + id),
                originDto,
                destinationDto
        );
    }

    public static Passenger createPassengerEntity(long id) {
        var passenger = new Passenger();
        passenger.setId(id);
        passenger.setFirstName("First " + id);
        passenger.setMiddleName("Middle " + id);
        passenger.setLastName("Last " + id);
        passenger.setDateOfBirth(LocalDate.of(1990, 1, (int) ((id % 28) + 1)));
        passenger.setAddress(createAddressEntity(id));
        return passenger;
    }

    public static PassengerDto createPassengerDto(long id) {
        var passenger = createPassengerEntity(id);
        return new PassengerDto(
                passenger.getId(),
                createAddressDto(id),
                passenger.getFirstName(),
                passenger.getMiddleName(),
                passenger.getLastName(),
                passenger.getDateOfBirth()
        );
    }

    public static Booking createBookingEntity(long id, Flight flight, Passenger passenger) {
        var booking = new Booking();
        booking.setId(id);
        booking.setCabinClass("Test Cabin Class " + id);
        booking.setSeat("Test Seat " + id);
        booking.setFlight(flight);
        booking.setPassenger(passenger);
        return booking;
    }

    public static BookingDto createBookingDto(long id, FlightDto flightDto, PassengerDto passengerDto) {
        return new BookingDto(
                id,
                flightDto,
                passengerDto,
                "Test Cabin Class " + id,
                "Test Seat " + id
        );
    }
}
