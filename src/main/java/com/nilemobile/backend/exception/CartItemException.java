package com.nilemobile.backend.exception;

public class CartItemException extends BaseApplicationException {
    public CartItemException(String message) {
        super(ErrorCode.CART_ITEM_NOT_FOUND, message);
    }

    public CartItemException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public CartItemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
