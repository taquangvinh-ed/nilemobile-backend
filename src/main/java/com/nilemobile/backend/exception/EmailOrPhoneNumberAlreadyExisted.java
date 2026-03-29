package com.nilemobile.backend.exception;

public class EmailOrPhoneNumberAlreadyExisted extends BaseApplicationException {
    public EmailOrPhoneNumberAlreadyExisted(String message) {
        super(ErrorCode.EMAIL_OR_PHONE_ALREADY_EXISTS, message);
    }

    public EmailOrPhoneNumberAlreadyExisted() {
        super(ErrorCode.EMAIL_OR_PHONE_ALREADY_EXISTS);
    }
}

