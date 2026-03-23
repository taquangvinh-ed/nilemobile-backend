package com.nilemobile.backend.exception;

public class PhoneNumberAlreadyExisted extends RuntimeException {
    public PhoneNumberAlreadyExisted(String message) {
        super(message);
    }
}
