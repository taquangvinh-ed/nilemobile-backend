package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.model.CartItem;
import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.model.OrderDetail;
import com.nilemobile.backend.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Override
    public OrderDetail createOrderDetail(CartItem cartItem, Order order) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setVariation(cartItem.getVariation());
        orderDetail.setQuantity(cartItem.getQuantity());
        orderDetail.setPrice(cartItem.getVariation().getPrice());
        orderDetail.setOrder(order);
        return orderDetail;
    }
}
