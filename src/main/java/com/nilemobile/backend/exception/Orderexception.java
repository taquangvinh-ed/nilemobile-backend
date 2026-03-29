package com.nilemobile.backend.exception;

public class Orderexception extends BaseApplicationException {
    public Orderexception(String message) {
        super(ErrorCode.ORDER_NOT_FOUND, message);
    }

    public Orderexception(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public Orderexception(ErrorCode errorCode) {
        super(errorCode);
    }
}
