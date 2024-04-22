package com.anonymous.exception;

import com.anonymous.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appExceptionHandler(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .statusCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }
}
