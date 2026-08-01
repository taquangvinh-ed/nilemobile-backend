package com.nilemobile.backend.service;

import com.nilemobile.backend.dto.CartItemDTO;
import com.nilemobile.backend.exception.CartItemException;
import com.nilemobile.backend.dto.request.CreateCartItemRequest;

public interface CartItemService {

    public CartItemDTO createCartItem(CreateCartItemRequest request);

    public CartItemDTO updateCartItem(Long userId, Long cartItemId, int quantity) throws CartItemException;

    public void removeCartItemFromCart(Long userId, Long cartItemId) throws CartItemException;

    void updateCartItemSelection(Long userId, Long cartItemId, Boolean selected) throws CartItemException;

}
