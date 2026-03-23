package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.AddressException;
import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.repository.AddressRepository;
import com.nilemobile.backend.repository.UserRepository;
import com.nilemobile.backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImp implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Address> getAddressesByUserId(Long userId) throws AddressException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new AddressException("User not found with id: " + userId);
        }
        User user = userOpt.get();
        return user.getAddresses();
    }

    @Override
    public Address addAddress(Address address, Long userId) throws AddressException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new AddressException("User not found with id: " + userId);
        }
        User user = userOpt.get();
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address, Long userId) throws AddressException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new AddressException("User not found with id: " + userId);
        }
        User user = userOpt.get();
        address.setUser(user);
        Optional<Address> existingAddressOpt = addressRepository.findById(address.getAddressId());
        if (existingAddressOpt.isEmpty()) {
            throw new AddressException("Address not found with id: " + address.getAddressId());
        }
        Address existingAddress = existingAddressOpt.get();
        if (!existingAddress.getUser().getUserId().equals(userId)) {
            throw new AddressException("Address does not belong to the user");
        }
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) throws AddressException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new AddressException("User not found with id: " + userId);
        }
        User user = userOpt.get();
        Optional<Address> addressOpt = addressRepository.findById(addressId);
        if (addressOpt.isEmpty()) {
            throw new AddressException("Address not found with id: " + addressId);
        }
        Address address = addressOpt.get();
        if (!address.getUser().getUserId().equals(userId)) {
            throw new AddressException("Address does not belong to the user");
        }
        addressRepository.delete(address);
    }
}
