package com.xkl.csvtest.services;

import com.xkl.csvtest.database.employee.Address;
import com.xkl.csvtest.dtos.AddressDto;
import com.xkl.csvtest.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Address addAddress(AddressDto address) {
        return addressRepository.save(new Address(address));
    }

    @Transactional
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
