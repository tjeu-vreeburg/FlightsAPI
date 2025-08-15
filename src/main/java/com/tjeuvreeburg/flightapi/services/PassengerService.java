package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericService;
import com.tjeuvreeburg.flightapi.entities.Passenger;
import com.tjeuvreeburg.flightapi.repositories.PassengerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PassengerService implements GenericService<Passenger> {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public void delete(long id) {
        passengerRepository.deleteById(id);
    }

    @Override
    public Passenger getById(long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("passenger", id));
    }

    @Override
    public Passenger save(Passenger passenger) {
        return passengerRepository.saveAndFlush(passenger);
    }

    @Override
    public Passenger update(long id, Passenger newPassenger) {
        return passengerRepository.findById(id)
                .map(passenger -> {
                    passenger.setFirstName(newPassenger.getFirstName());
                    passenger.setMiddleName(newPassenger.getMiddleName());
                    passenger.setLastName(newPassenger.getLastName());
                    passenger.setDateOfBirth(newPassenger.getDateOfBirth());

                    return passengerRepository.saveAndFlush(passenger);
                })
                .orElseThrow(() -> ResourceNotFoundException.of("passenger", id));
    }

    @Override
    public Page<Passenger> findAll(Pageable pageable) {
        return passengerRepository.findAll(pageable);
    }
}