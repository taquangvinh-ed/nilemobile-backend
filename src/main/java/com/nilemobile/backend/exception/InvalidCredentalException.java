package com.nilemobile.backend.exception;

public class InvalidCredentalException extends BaseApplicationException {
    public InvalidCredentalException(String message) {
        super(ErrorCode.INVALID_CREDENTIALS, message);
    }

    public InvalidCredentalException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}
