package com.nilemobile.backend.exception;

public class RoleNotFoundException extends BaseApplicationException {
    public RoleNotFoundException(String message) {
        super(ErrorCode.ROLE_NOT_FOUND, message);
    }

    public RoleNotFoundException() {
        super(ErrorCode.ROLE_NOT_FOUND);
    }
}
