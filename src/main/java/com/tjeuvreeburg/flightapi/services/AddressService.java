package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericSpecificationService;
import com.tjeuvreeburg.flightapi.entities.Address;
import com.tjeuvreeburg.flightapi.repositories.AddressRepository;
import com.tjeuvreeburg.flightapi.specifications.AddressSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AddressService implements GenericSpecificationService<Address, AddressSpecification> {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void delete(long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Address getById(long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("address", id));
    }

    @Override
    public Address save(Address address) {
        return addressRepository.saveAndFlush(address);
    }

    @Override
    public Address update(long id, Address newAddress) {
        return addressRepository.findById(id)
                .map(address -> {
                    address.setStreet(newAddress.getStreet());
                    address.setCity(newAddress.getCity());
                    address.setCountry(newAddress.getCountry());
                    address.setPostalCode(newAddress.getPostalCode());

                    return addressRepository.saveAndFlush(address);
                })
                .orElseThrow(() -> ResourceNotFoundException.of("address", id));
    }

    @Override
    public Page<Address> findAll(AddressSpecification specification, Pageable pageable) {
        return addressRepository.findAll(specification, pageable);
    }
}