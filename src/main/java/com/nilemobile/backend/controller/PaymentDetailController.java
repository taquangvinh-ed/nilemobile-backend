package com.nilemobile.backend.controller;

import com.nilemobile.backend.model.Order;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.ErrorResponse;
import com.nilemobile.backend.request.UpdatePaymentRequest;
import com.nilemobile.backend.service.OrderService;
import com.nilemobile.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentDetailController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentDetailService paymentDetailService;

    @Autowired
    private OrderService orderService;

    @PutMapping("/orders/{orderId}/update-payment-method")
    public ResponseEntity<?> updatePaymentMethod(@PathVariable Long orderId,
                                                 @RequestBody UpdatePaymentRequest req,
                                                 @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            if (user != null) {
                PaymentMethod paymentMethod = PaymentMethod.valueOf(req.getPaymentMethod());

                String result = paymentDetailService.updatePaymentMethod(orderId, paymentMethod);

                Order updatedOrder = orderService.findOrderById(orderId);
                return ResponseEntity.ok(updatedOrder);
            }


        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid payment method: " + req.getPaymentMethod()));
        }
        return ResponseEntity.badRequest().body(new ErrorResponse("Error in update method controller"));
    }

}
