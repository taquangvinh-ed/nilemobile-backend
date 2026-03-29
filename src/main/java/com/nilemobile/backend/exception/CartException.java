package com.nilemobile.backend.exception;

public class CartException extends BaseApplicationException {
    public CartException(String message) {
        super(ErrorCode.CART_NOT_FOUND, message);
    }

    public CartException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public CartException(ErrorCode errorCode) {
        super(errorCode);
    }
}
