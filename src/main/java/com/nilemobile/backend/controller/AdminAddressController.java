package com.nilemobile.backend.controller;

import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.dto.AddressDTO;
import com.nilemobile.backend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/addresses")
public class AdminAddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/user/id/{userId}")
    public ResponseEntity<List<AddressDTO>> getAllAddressesByUserId(@PathVariable Long userId) {
        List<Address> addresses = addressRepository.findByUserUserId(userId);
        List<AddressDTO> addressDTOs = addresses.stream()
                .map(address -> new AddressDTO(
                        address.getAddressId(),
                        address.getFirstName(),
                        address.getLastName(),
                        address.getPhoneNumber(),
                        address.getAddressLine(),
                        address.getWard(),
                        address.getDistrict(),
                        address.getProvince(),
                        address.getDefault()
                )).toList();
        return ResponseEntity.ok(addressDTOs);
    }
}
