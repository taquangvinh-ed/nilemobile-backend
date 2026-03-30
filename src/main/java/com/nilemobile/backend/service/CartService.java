package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.*;
import com.nilemobile.backend.dto.CartDTO;

public interface CartService {

    Cart createCart(Customer customer);
    
    CartDTO getCartByCustomerId(Long customerId) throws ProductException;
}
