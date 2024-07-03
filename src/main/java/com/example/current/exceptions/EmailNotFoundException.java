package com.example.current.exceptions;

import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends BaseException {
    public EmailNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
