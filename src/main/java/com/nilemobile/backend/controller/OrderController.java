package com.nilemobile.backend.controller;

import com.nilemobile.backend.auth.CustomUserDetail;
import com.nilemobile.backend.contant.SuccessCode;
import com.nilemobile.backend.dto.OrderDTO;
import com.nilemobile.backend.dto.reponse.ApiResponse;
import com.nilemobile.backend.exception.Orderexception;
import com.nilemobile.backend.mapper.OrderMapper;
import com.nilemobile.backend.model.Address;
import com.nilemobile.backend.model.Customer;
import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.service.OrderService;
import com.nilemobile.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<OrderDTO> createOrder(@AuthenticationPrincipal CustomUserDetail customerUserDetails, @RequestParam Long addressId) {
        Long userId = customerUserDetails.getUserId();
        OrderDTO orderDTO = orderService.createOrder(userId, addressId);
        return ApiResponse.<OrderDTO>builder()
                .success(true)
                .code(SuccessCode.CREATE_SUCCESS.getCode())
                .message(SuccessCode.CREATE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(orderDTO)
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrderDTO>> getUserOrders(
            @AuthenticationPrincipal CustomUserDetail customerUserDetails,
            @RequestParam(value = "status", required = false) String status) {

        Long userId = customerUserDetails.getUserId();
        List<OrderDTO> orderDTOs;
        if (status == null || status.isBlank() || status.equalsIgnoreCase("all")) {
            orderDTOs = orderService.getAllOrders(userId);
        } else {
            orderDTOs = orderService.getOrdersByUserAndStatus(userId,  status);
        }

        return ApiResponse.<List<OrderDTO>>builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(orderDTOs)
                .build();
    }

    @GetMapping("/filter")
    public ApiResponse<List<OrderDTO>> filterOrdersByStatus(@RequestParam String status) {
        List<OrderDTO> orderDTOs = orderService.filterOrderByStatus(status);

        return ApiResponse.<List<OrderDTO>>builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(orderDTOs)
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderDTO> getOrderById(@PathVariable Long orderId) {
        OrderDTO order = orderService.findOrderById(orderId);

        return ApiResponse.<OrderDTO>builder()
                .success(true)
                .code(SuccessCode.GET_SUCCESS.getCode())
                .message(SuccessCode.GET_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(order)
                .build();
    }

    @PutMapping("/{orderId}/confirm")
    public ApiResponse<OrderDTO> confirmOrder(@PathVariable Long orderId) {
        return statusResponse(orderService.confirmOrder(orderId));
    }

    @PutMapping("/{orderId}/process")
    public ApiResponse<OrderDTO> processOrder(@PathVariable Long orderId) {
        return statusResponse(orderService.processOrder(orderId));
    }

    @PutMapping("/{orderId}/ship")
    public ApiResponse<OrderDTO> shipOrder(@PathVariable Long orderId) {
        return statusResponse(orderService.shippedOrder(orderId));
    }

    @PutMapping("/{orderId}/deliver")
    public ApiResponse<OrderDTO> deliverOrder(@PathVariable Long orderId) {
        return statusResponse(orderService.deliveredOrder(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ApiResponse<OrderDTO> completeOrder(@PathVariable Long orderId) {
        return statusResponse(orderService.completeOrder(orderId));
    }

    @PutMapping("/{orderId}/cancel")
    public ApiResponse<OrderDTO> cancelOrder(@PathVariable Long orderId) {
        return statusResponse(orderService.canceledOrder(orderId));
    }

    @PutMapping("/{orderId}/update-shipping-address")
    public ApiResponse<OrderDTO> updateShippingAddress(@PathVariable Long orderId, @RequestBody Address shippingAddress) {
        OrderDTO orderDTO = orderService.updateShippingAddress(orderId, shippingAddress);

        return ApiResponse.<OrderDTO>builder()
                .success(true)
                .code(SuccessCode.UPDATE_SUCCESS.getCode())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(orderDTO)
                .build();
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);

        return ApiResponse.<Void>builder()
                .success(true)
                .code(SuccessCode.DELETE_SUCCESS.getCode())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    private ApiResponse<OrderDTO> statusResponse(OrderDTO orderDTO) {
        return ApiResponse.<OrderDTO>builder()
                .success(true)
                .code(SuccessCode.UPDATE_SUCCESS.getCode())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .body(orderDTO)
                .build();
    }
}
