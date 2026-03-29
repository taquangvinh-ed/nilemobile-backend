package com.nilemobile.backend.exception;

public class EmailAlreadyExisted extends BaseApplicationException {
    public EmailAlreadyExisted(String message) {
        super(ErrorCode.EMAIL_ALREADY_EXISTS, message);
    }

    public EmailAlreadyExisted() {
        super(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
}


