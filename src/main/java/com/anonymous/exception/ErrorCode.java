package com.anonymous.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED(9999, "Uncategorized error !", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(9998, "You don't have permission !", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(9997, "Unauthenticated error !", HttpStatus.UNAUTHORIZED),
    INVALID_KEY(9999, "Key not found in annotation for validation!", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1001, "User not found !", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1002, "Username must be at least {min} characters !", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1002, "Password must be at least {min} characters !", HttpStatus.BAD_REQUEST),
    USER_EXIST(1003, "User already exist !", HttpStatus.NOT_FOUND);


    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
