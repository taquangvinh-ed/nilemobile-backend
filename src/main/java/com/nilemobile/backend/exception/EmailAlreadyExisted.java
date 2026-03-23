package com.nilemobile.backend.exception;

public class EmailAlreadyExisted extends RuntimeException {
    public EmailAlreadyExisted(String message) {
        super(message);
    }
}
