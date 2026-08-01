//package com.nilemobile.backend.service;
//
//import com.nilemobile.backend.config.VNPayConfig;
//import com.nilemobile.backend.contant.OrderStatus;
//import com.nilemobile.backend.model.Order;
//import com.nilemobile.backend.dto.PaymentDTO;
//import com.nilemobile.backend.util.VNPayUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//public class VNPayService {
//
//    private final VNPayConfig vnPayConfig;
//    private final OrderService orderService;
//
//    public VNPayService(VNPayConfig vnPayConfig, OrderService orderService) {
//        this.vnPayConfig = vnPayConfig;
//        this.orderService = orderService;
//    }
//
//    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
//        String orderIdStr = request.getParameter("orderId");
//        Long orderId;
//        try {
//            orderId = Long.parseLong(orderIdStr);
//        } catch (NumberFormatException e) {
//            return PaymentDTO.VNPayResponse.builder()
//                    .code("99")
//                    .message("Invalid orderId format")
//                    .paymentUrl("")
//                    .build();
//        }
//
//        if (orderId == null) {
//            return PaymentDTO.VNPayResponse.builder()
//                    .code("99")
//                    .message("Missing orderId")
//                    .paymentUrl("")
//                    .build();
//        }
//
//        Order order;
//        try {
//            order = orderService.findOrderById(orderId);
//        } catch (Exception e) {
//            return PaymentDTO.VNPayResponse.builder()
//                    .code("98")
//                    .message("Order not found: " + e.getMessage())
//                    .paymentUrl("")
//                    .build();
//        }
//
//        if (!order.getStatus().equals(OrderStatus.PLACED)) {
//            return PaymentDTO.VNPayResponse.builder()
//                    .code("97")
//                    .message("Order cannot be paid because its status is: " + order.getStatus())
//                    .paymentUrl("")
//                    .build();
//        }
//
//        long amount = order.getTotalDiscountPrice();
//        long vnpAmount = amount * 100;
//        String bankCode = request.getParameter("bankCode");
//
//        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(orderId, amount);
//        vnpParamsMap.put("vnp_Amount", String.valueOf(vnpAmount));
//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnpParamsMap.put("vnp_BankCode", bankCode);
//        }
//        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
//        //build query url
//        String queryUrl = VNPayUtil.getPaymentUrl(vnpParamsMap, true);
//        String hashData = VNPayUtil.getPaymentUrl(vnpParamsMap, false);
//        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
//        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
//        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
//        return PaymentDTO.VNPayResponse.builder()
//                .code("ok")
//                .message("success")
//                .paymentUrl(paymentUrl).build();
//    }
//
//    public boolean verifyCallback(Map<String, String> params) {
//        String vnp_SecureHash = params.get("vnp_SecureHash");
//        params.remove("vnp_SecureHash");
//
//        String hashData = VNPayUtil.getPaymentUrl(params, false);
//        String computedHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
//
//        return vnp_SecureHash != null && vnp_SecureHash.equals(computedHash);
//    }
//
//
//}
