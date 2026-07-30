package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.dto.AddressDTO;
import com.nilemobile.backend.dto.request.AddAddressRequest;
import com.nilemobile.backend.exception.AddressException;
import com.nilemobile.backend.exception.CustomerNotFoundException;
import com.nilemobile.backend.mapper.AddressMapper;
import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.model.Customer;
import com.nilemobile.backend.repository.AddressRepository;
import com.nilemobile.backend.repository.CustomerRepository;
import com.nilemobile.backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImp implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CustomerRepository customerRepository;

    @Override
    public List<AddressDTO> getAddressesByCustomerId(Long customerId) throws AddressException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AddressException("Customer not found with id: " + customerId));
        return addressMapper.toDtoList(addressRepository.findByCustomer(customer));
    }

    @Override
    public AddressDTO addAddress(AddAddressRequest request, Long customerId) throws AddressException {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));

        Address address = addressMapper.requestToEntity(request);
        address.setCustomer(customer);

        Address savedAddress = addressRepository.save(address);
        return addressMapper.toDto(savedAddress);
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO, Long addressId) throws AddressException {

        Optional<Address> existingAddressOpt = addressRepository.findById(addressId);
        if (existingAddressOpt.isEmpty()) {
            throw new AddressException("Address not found with id: " + addressId);
        }
        Address existingAddress = existingAddressOpt.get();

        return addressMapper.partialUpdate(addressDTO, existingAddress);

    }

    @Override
    public void deleteAddress(Long addressId) throws AddressException {
       if (!addressRepository.existsById(addressId)) {
            throw new AddressException("Address not found with id: " + addressId);
        }
        addressRepository.deleteById(addressId);
    }
}
