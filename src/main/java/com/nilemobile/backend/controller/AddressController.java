package com.nilemobile.backend.controller;

import com.nilemobile.backend.exception.AddressException;
import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.AddressDTO;
import com.nilemobile.backend.service.AddressService;
import com.nilemobile.backend.exception.UserException;
import com.nilemobile.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getUserAddresses(@RequestHeader("Authorization") String jwt) throws UserException, AddressException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        List<Address> addresses = addressService.getAddressesByUserId(userId);

        List<AddressDTO> addressDTOs = addresses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(addressDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> addAddress(@RequestHeader("Authorization") String jwt, @RequestBody AddressDTO addressDTO) throws UserException, AddressException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        Address address = convertToEntity(addressDTO);
        address.setUser(user);

        Address savedAddress = addressService.addAddress(address, userId);
        return new ResponseEntity<>(convertToDTO(savedAddress), HttpStatus.CREATED);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@RequestHeader("Authorization") String jwt, @PathVariable Long addressId, @RequestBody AddressDTO addressDTO) throws UserException, AddressException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        Address address = convertToEntity(addressDTO);
        address.setAddressId(addressId);
        address.setUser(user);

        Address updatedAddress = addressService.updateAddress(address, userId);
        return new ResponseEntity<>(convertToDTO(updatedAddress), HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@RequestHeader("Authorization") String jwt, @PathVariable Long addressId) throws UserException, AddressException {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        addressService.deleteAddress(userId, addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private AddressDTO convertToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setFirstName(address.getFirstName());
        addressDTO.setLastName(address.getLastName());
        addressDTO.setPhoneNumber(address.getPhoneNumber());
        addressDTO.setAddressLine(address.getAddressLine());
        addressDTO.setWard(address.getWard());
        addressDTO.setDistrict(address.getDistrict());
        addressDTO.setProvince(address.getProvince());
        addressDTO.setIsDefault(address.getDefault());
        return addressDTO;
    }

    private Address convertToEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setAddressId(addressDTO.getAddressId());
        address.setFirstName(addressDTO.getFirstName());
        address.setLastName(addressDTO.getLastName());
        address.setPhoneNumber(addressDTO.getPhoneNumber());
        address.setAddressLine(addressDTO.getAddressLine() != null ? addressDTO.getAddressLine() : "");
        address.setWard(addressDTO.getWard() != null ? addressDTO.getWard() : "");
        address.setDistrict(addressDTO.getDistrict() != null ? addressDTO.getDistrict() : "");
        address.setProvince(addressDTO.getProvince() != null ? addressDTO.getProvince() : "");

        // Đảm bảo isDefault không bao giờ là null
        Boolean isDefault = addressDTO.getIsDefault();
        if (isDefault == null) {
            System.out.println("Warning: isDefault is null in AddressDTO, setting to false");
            isDefault = false;
        }
        address.setDefault(isDefault);

        return address;
    }
}
