package com.nilemobile.backend.controller;

import com.nilemobile.backend.auth.CustomUserDetail;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;

@RestController
@RequestMapping("/api/v1/customers/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ApiResponse<CartDTO> getCart(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
        Long userId = customUserDetail.getUserId();
        CartDTO cartDTO = cartService.getCartByCustomerId(userId);
        return ApiResponse.<CartDTO>builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(cartDTO)
                .build();
    }
}
