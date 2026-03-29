package com.nilemobile.backend.exception;

public class PhoneNumberAlreadyExisted extends BaseApplicationException {
    public PhoneNumberAlreadyExisted(String message) {
        super(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS, message);
    }

    public PhoneNumberAlreadyExisted() {
        super(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
    }
}
