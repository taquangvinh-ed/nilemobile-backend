//package com.nilemobile.backend.controller;
//
//import com.nilemobile.backend.dto.PaymentDTO;
//import com.nilemobile.backend.service.VNPayService;
//import com.nilemobile.backend.util.VNPayUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/payment-vnpay")
//public class VNPayController {
//    private final VNPayService vnPayService;
//
//    public VNPayController(VNPayService vnPayService) {
//        this.vnPayService = vnPayService;
//    }
//
//
//    @GetMapping
//    public ResponseEntity<?> pay(@RequestParam String orderId, HttpServletRequest request) {
//        request.setAttribute("orderId", orderId);
//        return new ResponseEntity<>(vnPayService.createVnPayPayment(request), HttpStatus.OK);
//    }
//
//    @GetMapping("/callback")
//    public ResponseEntity<?> payCallbackHandler(HttpServletRequest request,
//                                                HttpServletResponse response,
//                                                @RequestParam Map<String, String> allParams) throws IOException {
//        String vnp_ResponseCode = allParams.get("vnp_ResponseCode");
//
//        String redirectUrl;
//        if (vnp_ResponseCode == null) {
//            allParams.put("error", "missing_response_code");
//            String queryString = VNPayUtil.getPaymentUrl(allParams, true);
//            redirectUrl = "http://localhost:3000/payment-callback?" + queryString;
//        } else {
//            String queryString = VNPayUtil.getPaymentUrl(allParams, true);
//            redirectUrl = "http://localhost:3000/payment-callback?" + queryString;
//        }
//
//        String htmlResponse = "<html><body>" +
//                "<script>window.location.href = '" + redirectUrl + "';</script>" +
//                "</body></html>";
//
//        return new ResponseEntity<>(htmlResponse, HttpStatus.OK);
//    }
//
//    @GetMapping("/verify")
//    public ResponseEntity<?> verifyPayment(@RequestParam Map<String, String> allParams) {
//        String vnp_ResponseCode = allParams.get("vnp_ResponseCode");
//
//        if (vnp_ResponseCode == null) {
//            PaymentDTO.VNPayResponse responseBody = PaymentDTO.VNPayResponse.builder()
//                    .code("99")
//                    .message("Missing vnp_ResponseCode")
//                    .paymentUrl("")
//                    .build();
//            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
//        }
//
//        PaymentDTO.VNPayResponse responseBody = PaymentDTO.VNPayResponse.builder()
//                .code(vnp_ResponseCode)
//                .message(vnp_ResponseCode.equals("00") ? "success" : "failed")
//                .paymentUrl("")
//                .build();
//        return new ResponseEntity<>(responseBody, vnp_ResponseCode.equals("00") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
//    }
//}
