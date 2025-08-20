package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericService;
import com.tjeuvreeburg.flightapi.models.dto.PassengerDto;
import com.tjeuvreeburg.flightapi.models.entities.Passenger;
import com.tjeuvreeburg.flightapi.models.mappers.PassengerMapper;
import com.tjeuvreeburg.flightapi.repositories.AddressRepository;
import com.tjeuvreeburg.flightapi.repositories.PassengerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PassengerService implements GenericService<PassengerDto> {

    private final PassengerMapper passengerMapper;
    private final AddressRepository addressRepository;
    private final PassengerRepository passengerRepository;

    public PassengerService(
            PassengerMapper passengerMapper,
            AddressRepository addressRepository,
            PassengerRepository passengerRepository
    ) {
        this.passengerMapper = passengerMapper;
        this.addressRepository = addressRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    @Transactional
    public void delete(long id) {
        passengerRepository.deleteById(id);
    }

    @Override
    public PassengerDto getById(long id) {
        return passengerRepository.findById(id)
                .map(passengerMapper::toDto)
                .orElseThrow(() -> ResourceNotFoundException.of("passenger", id));
    }

    @Override
    @Transactional
    public PassengerDto save(PassengerDto passengerDto) {
        Passenger passenger = passengerMapper.toEntity(passengerDto);
        Passenger save = passengerRepository.saveAndFlush(passenger);
        return passengerMapper.toDto(save);
    }

    @Override
    @Transactional
    public PassengerDto update(long id, PassengerDto passengerDto) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("passenger", id));

        var firstName = passengerDto.firstName();
        if (firstName != null) {
            passenger.setFirstName(firstName);
        }

        var middleName = passengerDto.middleName();
        if (middleName != null) {
            passenger.setMiddleName(middleName);
        }

        var lastName = passengerDto.lastName();
        if (lastName != null) {
            passenger.setLastName(lastName);
        }

        var dateOfBirth = passengerDto.dateOfBirth();
        if (dateOfBirth != null) {
            passenger.setDateOfBirth(dateOfBirth);
        }

        var addressDto = passengerDto.address();
        if (addressDto != null && addressDto.id() != null) {
            var address = addressRepository.findById(addressDto.id())
                    .orElseThrow(() -> ResourceNotFoundException.of("address", addressDto.id()));
            passenger.setAddress(address);
        }

        Passenger save = passengerRepository.saveAndFlush(passenger);
        return passengerMapper.toDto(save);
    }


    @Override
    public Page<PassengerDto> findAll(Pageable pageable) {
        return passengerRepository.findAll(pageable).map(passengerMapper::toDto);
    }
}