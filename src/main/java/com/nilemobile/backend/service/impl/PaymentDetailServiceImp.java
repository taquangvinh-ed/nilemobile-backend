package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.model.PaymentDetails;
import com.nilemobile.backend.model.PaymentMethod;
import com.nilemobile.backend.model.PaymentStatus;
import com.nilemobile.backend.repository.OrderRepository;
import com.nilemobile.backend.repository.PaymentDetailRepository;
import com.nilemobile.backend.service.OrderService;
import com.nilemobile.backend.service.PaymentDetailService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentDetailServiceImp implements PaymentDetailService {
    private final OrderService orderService;

    private final PaymentDetailRepository paymentDetailRepository;

    private final OrderRepository orderRepository;

    public PaymentDetailServiceImp(OrderService orderService,
                                   PaymentDetailRepository paymentDetailRepository,
                                   OrderRepository orderRepository) {
        this.orderService = orderService;
        this.paymentDetailRepository = paymentDetailRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public String updatePaymentMethod(Long orderId, PaymentMethod paymentMethod) {
        Order order = orderService.findOrderById(orderId);

        if (order == null) {
            return "Error: Order with id:" + orderId + "not found";
        }

        PaymentDetails newPaymentDetails = new PaymentDetails();
        newPaymentDetails.setOrder(order);
        newPaymentDetails.setPaymentMethod(paymentMethod);
        newPaymentDetails.setStatus(PaymentStatus.PENDING);
        newPaymentDetails.setTransactionTime(LocalDateTime.now());
        newPaymentDetails.setAmount(order.getTotalPrice());


        order.setPaymentDetails(newPaymentDetails);

        paymentDetailRepository.save(newPaymentDetails);

        orderRepository.save(order);

        return "Payment method of order is updated successfully";
    }
}
