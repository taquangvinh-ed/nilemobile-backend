package com.nilemobile.backend.exception;

public class ProductException extends BaseApplicationException {
    public ProductException(String message) {
        super(ErrorCode.PRODUCT_NOT_FOUND, message);
    }

    public ProductException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ProductException(ErrorCode errorCode) {
        super(errorCode);
    }
}
