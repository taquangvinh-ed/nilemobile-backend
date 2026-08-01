package com.nilemobile.backend.service;

import com.nilemobile.backend.dto.AddressDTO;
import com.nilemobile.backend.dto.request.AddAddressRequest;
import com.nilemobile.backend.exception.AddressException;
import com.nilemobile.backend.model.Address;

import java.util.List;

public interface AddressService {

    List<AddressDTO> getAddressesByCustomerId(Long userId);

    AddressDTO addAddress(AddAddressRequest request, Long customerId);

    AddressDTO updateAddress(AddressDTO addressDTO, Long addressId);

    void deleteAddress(Long addressId);


}
