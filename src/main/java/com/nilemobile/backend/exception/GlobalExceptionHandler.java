package com.nilemobile.backend.exception;

import com.nilemobile.backend.reponse.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle all BaseApplicationException and its subclasses
     */
    @ExceptionHandler(BaseApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleBaseApplicationException(BaseApplicationException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        log.warn("Application exception: code={}, message={}", errorCode.getCode(), ex.getMessage());
       return ApiResponse.builder()
                .success(false)
                .code(errorCode.getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Handle PhoneNumberAlreadyExisted
     */
    @ExceptionHandler(PhoneNumberAlreadyExisted.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handlePhoneNumberAlreadyExisted(PhoneNumberAlreadyExisted ex) {
        log.warn("Phone number already exists: {}", ex.getMessage());
        return ApiResponse.builder()
                .success(false)
                .code(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS.getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
       
    }

    /**
     * Handle EmailAlreadyExisted
     */
    @ExceptionHandler(EmailAlreadyExisted.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleEmailAlreadyExisted(EmailAlreadyExisted ex) {
        log.warn("Email already exists: {}", ex.getMessage());
        return ApiResponse.builder()
                .success(false)
                .code(ErrorCode.EMAIL_ALREADY_EXISTS.getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Handle EmailOrPhoneNumberAlreadyExisted
     */
    @ExceptionHandler(EmailOrPhoneNumberAlreadyExisted.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleEmailOrPhoneNumberAlreadyExisted(EmailOrPhoneNumberAlreadyExisted ex) {
        log.warn("Email or phone number already exists: {}", ex.getMessage());
        return ApiResponse.builder()
                .success(false)
                .code(ErrorCode.EMAIL_OR_PHONE_ALREADY_EXISTS.getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Handle UserNotExistedException
     */
    @ExceptionHandler(UserNotExistedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<?> handleUserNotExistedException(UserNotExistedException ex) {
        log.warn("User not found: {}", ex.getMessage());
        return ApiResponse.builder()
                .success(false)
                .code(ErrorCode.USER_NOT_FOUND.getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Handle InvalidCredentalException
     */
    @ExceptionHandler(InvalidCredentalException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<?> handleInvalidCredentialException(InvalidCredentalException ex) {
        return ApiResponse.builder()
                .success(false)
                .code(ErrorCode.INVALID_CREDENTIALS.getCode())
                .message(ErrorCode.INVALID_CREDENTIALS.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<?> handleExpiredJwtException(ExpiredJwtException ex) {
        ErrorCode e = ErrorCode.EXPIRED_JWT;
        return ApiResponse.builder()
                .success(false)
                .code(e.getCode())
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<?> handleInvalidTokenException(InvalidTokenException ex) {
        ErrorCode e = ErrorCode.INVALID_JWT;
        return ApiResponse.builder()
                .success(false)
                .code(e.getCode())
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /*
     * Handle AddressException
     */
    
    @ExceptionHandler(CategoryAlreadyExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleCategoryAlreadyExistedException(CategoryAlreadyExistedException ex) {
        ErrorCode e = ErrorCode.CATEGORY_ALREADY_EXISTED;
        return ApiResponse.builder()
                .success(false)
                .code(e.getCode())
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }
    
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<?> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ErrorCode e = ErrorCode.CATEGORY_NOT_FOUND;
        return ApiResponse.builder()
                .success(false)
                .code(e.getCode())
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }
    
    @ExceptionHandler(AddressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleAddressException(AddressException ex) {
        log.warn("Address exception: {}", ex.getMessage());
        return ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Handle CartException
     */
    @ExceptionHandler(CartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleCartException(CartException ex) {
        log.warn("Cart exception: {}", ex.getMessage());
        return ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Handle CartItemException
     */
    @ExceptionHandler(CartItemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleCartItemException(CartItemException ex) {
        log.warn("Cart item exception: {}", ex.getMessage());
        return ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Handle ProductException
     */
    @ExceptionHandler(ProductException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleProductException(ProductException ex) {
        log.warn("Product exception: {}", ex.getMessage());
        return ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Handle OrderException
     */
    @ExceptionHandler(Orderexception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleOrderException(Orderexception ex) {
        log.warn("Order exception: {}", ex.getMessage());
        return ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Handle VariationException
     */
    @ExceptionHandler(VariationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleVariationException(VariationException ex) {
        log.warn("Variation exception: {}", ex.getMessage());
        return ApiResponse.builder()
                .success(false)
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Handle InvalidJwtException
     */
    @ExceptionHandler(InvalidJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<?> handleInvalidJwtException(InvalidJwtException ex) {
        log.warn("Invalid JWT: {}", ex.getMessage());
        return ApiResponse.builder()
                .success(false)
                .code(ErrorCode.INVALID_JWT.getCode())
                .message(ErrorCode.INVALID_JWT.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleGlobalException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        return ApiResponse.builder()
                .success(false)
                .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .message(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }
}
