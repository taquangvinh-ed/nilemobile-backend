package com.nilemobile.backend.service;

import com.nilemobile.backend.dto.CartDTO;
import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.*;

public interface CartService {

    CartDTO createCart(Customer customer);
    
    CartDTO getCartByCustomerId(Long customerId) throws ProductException;

}
