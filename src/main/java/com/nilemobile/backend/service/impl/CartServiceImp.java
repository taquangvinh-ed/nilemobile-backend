package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.*;
import com.nilemobile.backend.model.*;
import com.nilemobile.backend.dto.CartDTO;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.repository.CustomerRepository;
import com.nilemobile.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {

    private CartRepository cartRepository;
    private CustomerRepository customerRepository;

    @Override
    public Cart createCart(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        return cartRepository.save(cart);
    }

    @Override
    public CartDTO getCartByCustomerId(Long customerId) throws ProductException {
        Optional<Cart> cartOptional = cartRepository.findByCustomerCustomerId(customerId);
        if (cartOptional.isEmpty()) {
        throw new CartNotFoundException(ErrorCode.CART_NOT_FOUND.getMessage());
        }

    }


}
