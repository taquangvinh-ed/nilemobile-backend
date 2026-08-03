package com.nilemobile.backend.config;

import com.nilemobile.backend.util.VNPayUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Configuration
public class VNPayConfig {
    @Value("${PAY_URL}")
    private String vnp_PayUrl;

    @Value("${RETURN_URL}")
    private String vnp_ReturnUrl;

    @Value("${TMN_CODE}")
    private String vnp_TmnCode;

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Value("${VERSION}")
    private String vnp_Version;

    @Value("${COMMAND}")
    private String vnp_Command;

    @Value("${ORDER_TYPE}")
    private String orderType;

    public String getVnp_PayUrl() {
        return vnp_PayUrl;
    }

    public String getVnp_ReturnUrl() {
        return vnp_ReturnUrl;
    }

    public String getVnp_TmnCode() {
        return vnp_TmnCode;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getVnp_Version() {
        return vnp_Version;
    }

    public String getVnp_Command() {
        return vnp_Command;
    }

    public String getOrderType() {
        return orderType;
    }

    public Map<String, String> getVNPayConfig(Long orderId, Long amount) {
        Map<String, String> vnpParamsMap = new HashMap<>();

        vnpParamsMap.put("vnp_Version", this.vnp_Version);
        vnpParamsMap.put("vnp_Command", this.vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", this.vnp_TmnCode);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount * 100L));
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef", String.valueOf(orderId));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang " + orderId);
        vnpParamsMap.put("vnp_OrderType", this.orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = dateFormat.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = dateFormat.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        return vnpParamsMap;
    }
}
