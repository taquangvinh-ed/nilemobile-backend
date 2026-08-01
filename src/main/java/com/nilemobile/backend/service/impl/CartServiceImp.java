package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.dto.CartDTO;
import com.nilemobile.backend.exception.*;
import com.nilemobile.backend.mapper.CartMapper;
import com.nilemobile.backend.model.*;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public CartDTO createCart(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        Cart savedcart = cartRepository.save(cart);
        return cartMapper.toDto(savedcart);
    }

    @Override
    public CartDTO getCartByCustomerId(Long customerId) throws ProductException {
        Optional<Cart> cartOptional = cartRepository.findByCustomerCustomerId(customerId);
        if (cartOptional.isEmpty()) {
            throw new CartNotFoundException(ErrorCode.CART_NOT_FOUND.getMessage());
        }
        return cartMapper.toDto(cartOptional.get());
    }

    public long totalItemsInCart(Cart cart) {
        return cart.getCartItems().stream()
                .mapToLong(CartItem::getQuantity)
                .sum();
    }

    public double totalPriceInCart(Cart cart) {
        return cart.getCartItems().stream()
                .filter(CartItem::isSelected)
                .mapToDouble(CartItem::getSubtotal)
                .sum();
    }


}
