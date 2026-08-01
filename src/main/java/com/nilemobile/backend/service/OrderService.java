package com.nilemobile.backend.service;

import com.nilemobile.backend.exception.Orderexception;
import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.dto.OrderDTO;

import java.util.List;
import java.util.Map;

public interface OrderService {

    public OrderDTO createOrder(Long userId, Long addressId) throws Orderexception;

    public OrderDTO findOrderById(Long orderId) throws Orderexception;

    public List<OrderDTO> orderHistory(Long userId);

    public OrderDTO confirmOrder(Long orderId) throws Orderexception;

    OrderDTO processOrder(Long orderId) throws Orderexception;

    public OrderDTO shippedOrder(Long orderId) throws Orderexception;

     OrderDTO deliveredOrder(Long orderId) throws Orderexception;

    OrderDTO completeOrder(Long orderId) throws Orderexception;

    public OrderDTO canceledOrder(Long orderId) throws Orderexception;

    public List<OrderDTO> getAllOrders(Long userId);

    public void deleteOrder(Long orderId) throws Orderexception;

    public List<OrderDTO> filterOrderByStatus(String status);

    OrderDTO updateShippingAddress(Long orderId, Address shippingAddress) throws Orderexception;

    List<OrderDTO> getOrdersByUserAndStatus(Long userId, String status);
}
