package com.nilemobile.backend.exception;

import com.nilemobile.backend.reponse.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle all BaseApplicationException and its subclasses
     */
    @ExceptionHandler(BaseApplicationException.class)
    public ResponseEntity<ApiResponse<?>> handleBaseApplicationException(BaseApplicationException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.warn("Application exception: code={}, message={}", errorCode.getCode(), ex.getMessage());
        
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(errorCode.getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle PhoneNumberAlreadyExisted
     */
    @ExceptionHandler(PhoneNumberAlreadyExisted.class)
    public ResponseEntity<ApiResponse<?>> handlePhoneNumberAlreadyExisted(PhoneNumberAlreadyExisted ex) {
        log.warn("Phone number already exists: {}", ex.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS.getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle EmailAlreadyExisted
     */
    @ExceptionHandler(EmailAlreadyExisted.class)
    public ResponseEntity<ApiResponse<?>> handleEmailAlreadyExisted(EmailAlreadyExisted ex) {
        log.warn("Email already exists: {}", ex.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ErrorCode.EMAIL_ALREADY_EXISTS.getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle EmailOrPhoneNumberAlreadyExisted
     */
    @ExceptionHandler(EmailOrPhoneNumberAlreadyExisted.class)
    public ResponseEntity<ApiResponse<?>> handleEmailOrPhoneNumberAlreadyExisted(EmailOrPhoneNumberAlreadyExisted ex) {
        log.warn("Email or phone number already exists: {}", ex.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ErrorCode.EMAIL_OR_PHONE_ALREADY_EXISTS.getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle UserNotExistedException
     */
    @ExceptionHandler(UserNotExistedException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotExistedException(UserNotExistedException ex) {
        log.warn("User not found: {}", ex.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ErrorCode.USER_NOT_FOUND.getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handle InvalidCredentalException
     */
    @ExceptionHandler(InvalidCredentalException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidCredentialException(InvalidCredentalException ex) {
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ErrorCode.INVALID_CREDENTIALS.getCode())
                .message(ErrorCode.INVALID_CREDENTIALS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<?>> handleExpiredJwtException(ExpiredJwtException ex) {
        ErrorCode e = ErrorCode.EXPIRED_JWT;
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(e.getCode())
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidTokenException(InvalidTokenException ex) {
        ErrorCode e = ErrorCode.INVALID_JWT;
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)                .code(e.getCode())
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /*
     * Handle AddressException
     */
    @ExceptionHandler(AddressException.class)
    public ResponseEntity<ApiResponse<?>> handleAddressException(AddressException ex) {
        log.warn("Address exception: {}", ex.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle CartException
     */
    @ExceptionHandler(CartException.class)
    public ResponseEntity<ApiResponse<?>> handleCartException(CartException ex) {
        log.warn("Cart exception: {}", ex.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle CartItemException
     */
    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<ApiResponse<?>> handleCartItemException(CartItemException ex) {
        log.warn("Cart item exception: {}", ex.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle ProductException
     */
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ApiResponse<?>> handleProductException(ProductException ex) {
        log.warn("Product exception: {}", ex.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle OrderException
     */
    @ExceptionHandler(Orderexception.class)
    public ResponseEntity<ApiResponse<?>> handleOrderException(Orderexception ex) {
        log.warn("Order exception: {}", ex.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle VariationException
     */
    @ExceptionHandler(VariationException.class)
    public ResponseEntity<ApiResponse<?>> handleVariationException(VariationException ex) {
        log.warn("Variation exception: {}", ex.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle InvalidJwtException
     */
    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidJwtException(InvalidJwtException ex) {
        log.warn("Invalid JWT: {}", ex.getMessage());
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ErrorCode.INVALID_JWT.getCode())
                .message(ErrorCode.INVALID_JWT.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobalException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .message(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
