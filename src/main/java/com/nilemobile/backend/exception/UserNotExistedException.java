package com.nilemobile.backend.exception;

public class UserNotExistedException extends BaseApplicationException {
    public UserNotExistedException(String message) {
        super(ErrorCode.USER_NOT_FOUND, message);
    }

    public UserNotExistedException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
