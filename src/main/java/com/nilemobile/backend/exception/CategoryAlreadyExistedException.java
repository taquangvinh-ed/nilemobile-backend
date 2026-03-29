package com.nilemobile.backend.exception;

public class CategoryAlreadyExistedException extends RuntimeException {
    public CategoryAlreadyExistedException(String message) {
        super(message);
    }
}
