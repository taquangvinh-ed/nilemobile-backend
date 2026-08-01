package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.contant.OrderStatus;
import com.nilemobile.backend.exception.Orderexception;
import com.nilemobile.backend.mapper.OrderMapper;
import com.nilemobile.backend.model.*;
import com.nilemobile.backend.repository.CartRepository;
import com.nilemobile.backend.dto.OrderDTO;
import com.nilemobile.backend.repository.OrderRepository;
import com.nilemobile.backend.repository.VariationRepository;
import com.nilemobile.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final CartRepository cartRepository;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final CartItemService cartItemService;

    private final OrderDetailService orderDetailService;

    @Override
    @Transactional
    public OrderDTO createOrder(Long customerId, Long addressId) {
        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(()-> new Orderexception("Cart not found for customer with ID: " + customerId));
        Order newOrder = new Order();
        newOrder.setCustomer(cart.getCustomer());
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setShippingAddress(cart.getCustomer().getAddresses().stream()
                .filter(address -> address.getAddressId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new Orderexception("Address not found "+ "for customer with ID: " + customerId + " and address ID: " + addressId)));
        newOrder.setStatus(OrderStatus.PLACED);
        newOrder.setOrderDetails(new ArrayList<>());
        cart.getCartItems().stream()
                .filter(CartItem::isSelected)
                .toList().forEach(cartItem -> {
                    OrderDetail orderDetail = orderDetailService.createOrderDetail(cartItem, newOrder);
                    newOrder.getOrderDetails().add(orderDetail);
                    cartItemService.removeCartItemFromCart(customerId, cartItem.getCartItemId());
                });
        newOrder.setTotalPrice(newOrder.getOrderDetails().stream()
                .mapToLong(orderDetail -> orderDetail.getPrice() * orderDetail.getQuantity())
                .sum());
        newOrder.setTotalItem(newOrder.getOrderDetails().stream()
                .mapToInt(OrderDetail::getQuantity)
                .sum());
        Order savedOrder = orderRepository.save(newOrder);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO findOrderById(Long orderId) throws Orderexception {
        return orderRepository.findById(orderId)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new Orderexception("Order not found with id: " + orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> orderHistory(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId).stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .toList();
        return orderMapper.toDtoList(orders);
    }

    @Override
    @Transactional
    public OrderDTO confirmOrder(Long orderId) throws Orderexception {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.CONFIRMED);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDTO processOrder(Long orderId) throws Orderexception {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.PROCESSING);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDTO shippedOrder(Long orderId) throws Orderexception {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.SHIPPED);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public OrderDTO deliveredOrder(Long orderId) throws Orderexception {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.DELIVERED);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDTO completeOrder(Long orderId) throws Orderexception {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.COMPLETED);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDTO canceledOrder(Long orderId) throws Orderexception {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.CANCELED);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) throws Orderexception {
        Order order = getOrderById(orderId);
        orderRepository.delete(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> filterOrderByStatus(String status) {
        return orderRepository.findByStatus(parseStatus(status)).stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderDTO updateShippingAddress(Long orderId, Address shippingAddress) throws Orderexception {
        Order order = getOrderById(orderId);
        order.setShippingAddress(shippingAddress);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByUserAndStatus(Long userId, String status) {
        OrderStatus orderStatus = parseStatus(status);
        return orderRepository.findByUserId(userId).stream()
                .filter(order -> order.getStatus() == orderStatus)
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .map(orderMapper::toDto)
                .toList();
    }

    private OrderStatus parseStatus(String status) {
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Orderexception("Invalid order status: " + status);
        }
    }

    private Order getOrderById(Long orderId) throws Orderexception {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Orderexception("Order not found with id: " + orderId));
    }
}
