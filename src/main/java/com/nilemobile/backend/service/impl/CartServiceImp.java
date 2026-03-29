package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.exception.CartException;
import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.*;
import com.nilemobile.backend.repository.CartItemRepository;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.service.CartItemService;
import com.nilemobile.backend.service.CartService;
import com.nilemobile.backend.service.ProductService;
import com.nilemobile.backend.service.VariationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {

    private CartRepository cartRepository;

    @Override
    public Cart createCart(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        return cartRepository.save(cart);
    }



}
