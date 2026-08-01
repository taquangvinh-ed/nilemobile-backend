package com.nilemobile.backend.controller;

import com.nilemobile.backend.contant.SuccessCode;
import com.nilemobile.backend.dto.CartDTO;
import com.nilemobile.backend.dto.reponse.ApiResponse;
import com.nilemobile.backend.exception.CartException;
import com.nilemobile.backend.exception.ErrorCode;
import com.nilemobile.backend.model.Customer;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.service.CartService;
import com.nilemobile.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;

@RestController
@RequestMapping("/api/user/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final UserService userService;

    @GetMapping
    public ApiResponse<CartDTO> getCart(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        Customer customer = user.getCustomer();
        if (customer == null) {
            throw new CartException(ErrorCode.CART_NOT_FOUND, "Cart not found for user with id: " + user.getUserId());
        }

        CartDTO cartDTO = cartService.getCartByCustomerId(customer.getCustomerId());
        return ApiResponse.<CartDTO>builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(cartDTO)
                .build();
    }
}
