package com.tjeuvreeburg.flightapi.services;

import com.tjeuvreeburg.flightapi.base.exceptions.ResourceNotFoundException;
import com.tjeuvreeburg.flightapi.base.interfaces.GenericSpecificationService;
import com.tjeuvreeburg.flightapi.models.dto.AddressDto;
import com.tjeuvreeburg.flightapi.models.entities.Address;
import com.tjeuvreeburg.flightapi.models.mappers.AddressMapper;
import com.tjeuvreeburg.flightapi.repositories.AddressRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AddressService implements GenericSpecificationService<Address, AddressDto> {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressService(
            AddressRepository addressRepository,
            AddressMapper addressMapper
    ) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    @Transactional
    public void delete(long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public AddressDto getById(long id) {
        return addressRepository.findById(id)
                .map(addressMapper::toDto)
                .orElseThrow(() -> ResourceNotFoundException.of("address", id));
    }

    @Override
    @Transactional
    public AddressDto save(AddressDto addressDto) {
        var address = addressMapper.toEntity(addressDto);
        var save = addressRepository.saveAndFlush(address);
        return addressMapper.toDto(save);
    }

    @Override
    @Transactional
    public AddressDto update(long id, AddressDto addressDto) {
        return addressRepository.findById(id)
                .map(address -> {
                    address.setStreet(addressDto.street());
                    address.setCity(addressDto.city());
                    address.setCountry(addressDto.country());
                    address.setPostalCode(addressDto.postalCode());

                    Address savedAddress = addressRepository.saveAndFlush(address);
                    return addressMapper.toDto(savedAddress);
                })
                .orElseThrow(() -> ResourceNotFoundException.of("address", id));
    }

    @Override
    public Page<AddressDto> findAll(Specification<Address> specification, Pageable pageable) {
        return addressRepository.findAll(specification, pageable).map(addressMapper::toDto);
    }
}