package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.ProductException;
import com.nilemobile.backend.model.*;
import com.nilemobile.backend.request.AddCartItemRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CartService {

    public Cart createCart(Customer customer);

}
