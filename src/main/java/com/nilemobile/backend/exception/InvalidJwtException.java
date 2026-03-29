package com.nilemobile.backend.exception;

public class InvalidJwtException extends BaseApplicationException {
    public InvalidJwtException(String message) {
        super(ErrorCode.INVALID_JWT, message);
    }

    public InvalidJwtException() {
        super(ErrorCode.INVALID_JWT);
    }
}
