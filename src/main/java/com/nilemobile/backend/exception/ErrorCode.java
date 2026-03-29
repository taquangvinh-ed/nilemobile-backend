package com.nilemobile.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // User related errors
    USER_NOT_FOUND(1001, "User not found"),
    EMAIL_ALREADY_EXISTS(1002, "Email already exists"),
    PHONE_NUMBER_ALREADY_EXISTS(1003, "Phone number already exists"),
    EMAIL_OR_PHONE_ALREADY_EXISTS(1004, "Email or phone number already exists"),
    INVALID_CREDENTIALS(1005, "Invalid email or password"),

    EXPIRED_JWT(1006, "JWT token expired"),

    // Address related errors
    ADDRESS_NOT_FOUND(2001, "Address not found"),
    INVALID_ADDRESS(2002, "Invalid address information"),

    // Product related errors
    PRODUCT_NOT_FOUND(3001, "Product not found"),
    INVALID_PRODUCT(3002, "Invalid product information"),
    PRODUCT_OUT_OF_STOCK(3003, "Product out of stock"),

    // Cart related errors
    CART_NOT_FOUND(4001, "Cart not found"),
    INVALID_CART(4002, "Invalid cart information"),
    CART_ITEM_NOT_FOUND(4003, "Cart item not found"),

    // Order related errors
    ORDER_NOT_FOUND(5001, "Order not found"),
    INVALID_ORDER(5002, "Invalid order information"),
    ORDER_STATUS_ERROR(5003, "Invalid order status"),

    // Variation related errors
    VARIATION_NOT_FOUND(6001, "Variation not found"),
    INVALID_VARIATION(6002, "Invalid variation information"),

    // Review related errors
    REVIEW_NOT_FOUND(7001, "Review not found"),
    INVALID_REVIEW(7002, "Invalid review information"),

    // JWT/Authentication errors
    INVALID_JWT(8001, "Invalid JWT token"),
    JWT_EXPIRED(8002, "JWT token expired"),
    UNAUTHORIZED(8003, "Unauthorized access"),

    // Server errors
    INTERNAL_SERVER_ERROR(9000, "An unexpected error occurred"),
    VALIDATION_ERROR(9001, "Validation error"),


    //Token related errors
    INVLID_TOKEN(8004, "Invalid token")

    //Category related errors
    ,CATEGORY_NOT_FOUND(10001, "Category not found")
    ,CATEGORY_ALREADY_EXISTED(10002, "Category already existed")
    ;

    private final int code;
    private final String message;


}
