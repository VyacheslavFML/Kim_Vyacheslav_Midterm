package com.example.kim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<com.example.kim.exception.ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        com.example.kim.exception.ErrorResponse response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                validationErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<com.example.kim.exception.ErrorResponse> handleStudentNotFound(StudentNotFoundException ex) {
        com.example.kim.exception.ErrorResponse response = buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(com.example.kim.exception.DuplicateEmailException.class)
    public ResponseEntity<com.example.kim.exception.ErrorResponse> handleDuplicateEmail(com.example.kim.exception.DuplicateEmailException ex) {
        com.example.kim.exception.ErrorResponse response = buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(com.example.kim.exception.BusinessException.class)
    public ResponseEntity<com.example.kim.exception.ErrorResponse> handleBusinessException(com.example.kim.exception.BusinessException ex) {
        com.example.kim.exception.ErrorResponse response = buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<com.example.kim.exception.ErrorResponse> handleFallback(Exception ex) {
        com.example.kim.exception.ErrorResponse response = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected server error",
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private com.example.kim.exception.ErrorResponse buildErrorResponse(HttpStatus status, String message, Map<String, String> validationErrors) {
        return com.example.kim.exception.ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .validationErrors(validationErrors)
                .build();
    }
}
