package com.nilemobile.backend.controller;

import com.nilemobile.backend.exception.Orderexception;
import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.dto.AdminOrderDTO;
import com.nilemobile.backend.dto.OrderDetailDTO;
import com.nilemobile.backend.dto.OrderStatusDTO;
import com.nilemobile.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/list")
    public ResponseEntity<List<AdminOrderDTO>> getOrderList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        List<Order> orders = orderRepository.findAll();
        List<AdminOrderDTO> orderProfile = orders.stream().map(order -> new AdminOrderDTO(
                order.getId(),
                order.getShippingAddress().getFullName(),
                order.getUser().getPhoneNumber(),
                order.getTotalDiscountPrice(),
                order.getStatus().toString(),
                order.getOrderDate().format(formatter),
                order.getShippingAddress().getFullAddress(),
                order.getPaymentDetails().getPaymentMethod().toString(),
                order.getOrderDetails().stream().map(orderDetail -> new OrderDetailDTO(
                        orderDetail.getId(),
                        orderDetail.getVariation().getId(),
                        orderDetail.getVariation().getVariationName(),
                        orderDetail.getVariation().getProduct().getCategories().getParentCategory().getName(),
                        orderDetail.getVariation().getProduct().getCategories().getName(),
                        orderDetail.getVariation().getImageURL(),
                        orderDetail.getQuantity(),
                        orderDetail.getSubtotal(),
                        orderDetail.getTotalDiscountPrice()
                )).collect(toList())
        )).collect(toList());
        return ResponseEntity.ok(orderProfile);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<AdminOrderDTO> getOrderById(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new Orderexception("Order not found"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        AdminOrderDTO orderDTO = new AdminOrderDTO(
                order.getId(),
                order.getShippingAddress().getFullName(),
                order.getShippingAddress().getPhoneNumber(),
                order.getTotalDiscountPrice(),
                order.getStatus().toString(),
                order.getOrderDate().format(formatter),
                order.getShippingAddress().getFullAddress(),
                order.getPaymentDetails().getPaymentMethod().toString(),
                order.getOrderDetails().stream().map(orderDetail -> new OrderDetailDTO(
                        orderDetail.getId(),
                        orderDetail.getVariation().getId(),
                        orderDetail.getVariation().getVariationName(),
                        orderDetail.getVariation().getProduct().getCategories().getParentCategory().getName(),
                        orderDetail.getVariation().getProduct().getCategories().getName(),
                        orderDetail.getVariation().getImageURL(),
                        orderDetail.getQuantity(),
                        orderDetail.getSubtotal(),
                        orderDetail.getTotalDiscountPrice()
                )).collect(toList())
        );
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/user/id/{userId}")
    public ResponseEntity<List<AdminOrderDTO>> getAllOrdersByUserId(@PathVariable Long userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        List<Order> orders = orderRepository.findByUserId(userId);
        List<AdminOrderDTO> orderProfile = orders.stream().map(order -> new AdminOrderDTO(
                order.getId(),
                order.getShippingAddress().getFullName(),
                order.getUser().getPhoneNumber(),
                order.getTotalDiscountPrice(),
                order.getStatus().toString(),
                order.getOrderDate().format(formatter),
                order.getShippingAddress().getFullAddress(),
                order.getPaymentDetails().getPaymentMethod().toString(),
                order.getOrderDetails().stream().map(orderDetail -> new OrderDetailDTO(
                        orderDetail.getId(),
                        orderDetail.getVariation().getId(),
                        orderDetail.getVariation().getVariationName(),
                        orderDetail.getVariation().getProduct().getCategories().getParentCategory().getName(),
                        orderDetail.getVariation().getProduct().getCategories().getName(),
                        orderDetail.getVariation().getImageURL(),
                        orderDetail.getQuantity(),
                        orderDetail.getSubtotal(),
                        orderDetail.getTotalDiscountPrice()
                )).collect(toList())
        )).collect(toList());
        return ResponseEntity.ok(orderProfile);
    }

    @PutMapping("/updateStatus/{orderId}")
    public ResponseEntity<AdminOrderDTO> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusDTO request) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new Orderexception("Order not found"));
        order.setStatus(request.getStatus());
        orderRepository.save(order);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        AdminOrderDTO orderDTO = new AdminOrderDTO(
                order.getId(),
                order.getShippingAddress().getFullName(),
                order.getShippingAddress().getPhoneNumber(),
                order.getTotalDiscountPrice(),
                order.getStatus().toString(),
                order.getOrderDate().format(formatter),
                order.getShippingAddress().getFullAddress(),
                order.getPaymentDetails().getPaymentMethod().toString(),
                order.getOrderDetails().stream().map(orderDetail -> new OrderDetailDTO(
                        orderDetail.getId(),
                        orderDetail.getVariation().getId(),
                        orderDetail.getVariation().getVariationName(),
                        orderDetail.getVariation().getProduct().getCategories().getParentCategory().getName(),
                        orderDetail.getVariation().getProduct().getCategories().getName(),
                        orderDetail.getVariation().getImageURL(),
                        orderDetail.getQuantity(),
                        orderDetail.getSubtotal(),
                        orderDetail.getTotalDiscountPrice()
                )).collect(toList())
        );
        return ResponseEntity.ok(orderDTO);
    }
}