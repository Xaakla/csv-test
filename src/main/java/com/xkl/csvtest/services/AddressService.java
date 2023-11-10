package com.xkl.csvtest.services;

import com.xkl.csvtest.database.employee.Address;
import com.xkl.csvtest.dtos.AddressDto;
import com.xkl.csvtest.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressDto> findAllAddresses() {
        return addressRepository.findAll().stream().map(AddressDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Address addAddress(AddressDto address) {
        return addressRepository.save(new Address(address));
    }

    @Transactional
    public Address editAddress(Long id, AddressDto addressDto) {
        var address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Could not find address with id %s", id)));

        address.setUf(addressDto.getUf());
        address.setCity(addressDto.getCity());
        address.setNeighbourhood(addressDto.getNeighbourhood());
        address.setPlace(addressDto.getPlace());
        address.setComplement(addressDto.getComplement());

        return addressRepository.save(address);
    }

    @Transactional
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}
