package com.nilemobile.backend.exception;

public class AddressException extends BaseApplicationException {
    public AddressException(String message) {
        super(ErrorCode.ADDRESS_NOT_FOUND, message);
    }

    public AddressException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public AddressException(ErrorCode errorCode) {
        super(errorCode);
    }
}
