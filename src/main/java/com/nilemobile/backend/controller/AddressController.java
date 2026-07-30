package com.nilemobile.backend.controller;

import com.nilemobile.backend.dto.request.AddAddressRequest;
import com.nilemobile.backend.exception.AddressException;
import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.dto.AddressDTO;
import com.nilemobile.backend.service.AddressService;
import com.nilemobile.backend.exception.UserException;
import com.nilemobile.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getCustomerAddresses(@RequestHeader("Authorization") String jwt) throws UserException, AddressException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        List<AddressDTO> addresses = addressService.getAddressesByCustomerId(userId);

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> addAddress(@RequestHeader("Authorization") String jwt, @RequestBody AddAddressRequest request){
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();
        AddressDTO newAddress = addressService.addAddress(request, userId);

        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@RequestHeader("Authorization") String jwt, @PathVariable Long addressId, @RequestBody AddressDTO addressDTO){
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        AddressDTO updatedAddress = addressService.updateAddress(addressDTO, addressId);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@RequestHeader("Authorization") String jwt, @PathVariable Long addressId){
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        addressService.deleteAddress(addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
