package com.nilemobile.backend.controller;

import com.nilemobile.backend.contant.SuccessCode;
import com.nilemobile.backend.dto.AddressDTO;
import com.nilemobile.backend.dto.reponse.ApiResponse;
import com.nilemobile.backend.dto.request.AddAddressRequest;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.service.AddressService;
import com.nilemobile.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/user/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    private final UserService userService;

    @GetMapping
    public ApiResponse<List<AddressDTO>> getCustomerAddresses(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        List<AddressDTO> addresses = addressService.getAddressesByCustomerId(userId);

        return ApiResponse.<List<AddressDTO>>builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(addresses)
                .build();
    }

    @PostMapping
    public ApiResponse<AddressDTO> addAddress(@RequestHeader("Authorization") String jwt, @RequestBody AddAddressRequest request) {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        AddressDTO newAddress = addressService.addAddress(request, userId);

        return ApiResponse.<AddressDTO>builder()
                .success(true)
                .code(SuccessCode.CREATE_SUCCESS.getCode())
                .message(SuccessCode.CREATE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(newAddress)
                .build();
    }

    @PutMapping("/{addressId}")
    public ApiResponse<AddressDTO> updateAddress(@RequestHeader("Authorization") String jwt, @PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        AddressDTO updatedAddress = addressService.updateAddress(addressDTO, addressId);

        return ApiResponse.<AddressDTO>builder()
                .success(true)
                .code(SuccessCode.UPDATE_SUCCESS.getCode())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(updatedAddress)
                .build();
    }

    @DeleteMapping("/{addressId}")
    public ApiResponse<Void> deleteAddress(@RequestHeader("Authorization") String jwt, @PathVariable Long addressId) {
        User user = userService.findUserProfileByJwt(jwt);
        Long userId = user.getUserId();

        addressService.deleteAddress(addressId);

        return ApiResponse.<Void>builder()
                .success(true)
                .code(SuccessCode.DELETE_SUCCESS.getCode())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }
}
