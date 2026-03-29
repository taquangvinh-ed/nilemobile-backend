package com.nilemobile.backend.exception;

public class VariationException extends BaseApplicationException {
    public VariationException(String message) {
        super(ErrorCode.VARIATION_NOT_FOUND, message);
    }

    public VariationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public VariationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
