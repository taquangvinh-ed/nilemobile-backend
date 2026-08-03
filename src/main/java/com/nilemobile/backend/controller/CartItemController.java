package com.nilemobile.backend.controller;

import com.nilemobile.backend.contant.SuccessCode;
import com.nilemobile.backend.dto.CartItemDTO;
import com.nilemobile.backend.dto.reponse.ApiResponse;
import com.nilemobile.backend.dto.request.CreateCartItemRequest;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.service.CartItemService;
import com.nilemobile.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;

@RestController
@RequestMapping(value = "/api/v1/customers/cart/items", produces = "application/json")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    private final UserService userService;

    @PostMapping
    public ApiResponse<CartItemDTO> addCartItemToCart(@RequestBody CreateCartItemRequest request) {
        CartItemDTO cartItemDTO = cartItemService.createCartItem(request);
        return ApiResponse.<CartItemDTO>builder()
                .success(true)
                .code(SuccessCode.CREATE_SUCCESS.getCode())
                .message(SuccessCode.CREATE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(cartItemDTO)
                .build();
    }

    @PutMapping("/{cartItemId}")
    public ApiResponse<CartItemDTO> updateCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {
        User user = userService.findUserProfileByJwt(jwt);
        CartItemDTO cartItemDTO = cartItemService.updateCartItem(user.getUserId(), cartItemId, quantity);
        return ApiResponse.<CartItemDTO>builder()
                .success(true)
                .code(SuccessCode.UPDATE_SUCCESS.getCode())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(cartItemDTO)
                .build();
    }

    @DeleteMapping("/{cartItemId}")
    public ApiResponse<?> removeCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId) {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItemFromCart(user.getUserId(), cartItemId);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.DELETE_SUCCESS.getCode())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    @PutMapping("/{cartItemId}/select")
    public ApiResponse<?> updateCartItemSelection(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId,
            @RequestParam Boolean selected) {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.updateCartItemSelection(user.getUserId(), cartItemId, selected);
        return ApiResponse.builder()
                .success(true)
                .code(SuccessCode.UPDATE_SUCCESS.getCode())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }
}
