package com.nilemobile.backend.exception;

/**
 * Base exception class for all application exceptions.
 * All business logic exceptions should extend this class.
 */
public class BaseApplicationException extends RuntimeException {
    private final ErrorCode errorCode;

    public BaseApplicationException(String message) {
        super(message);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public BaseApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseApplicationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseApplicationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

