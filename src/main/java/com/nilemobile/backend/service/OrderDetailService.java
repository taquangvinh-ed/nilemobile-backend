package com.nilemobile.backend.service;

import com.nilemobile.backend.model.CartItem;
import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.model.OrderDetail;

public interface OrderDetailService {
    OrderDetail createOrderDetail(CartItem cartItem, Order order);
}
