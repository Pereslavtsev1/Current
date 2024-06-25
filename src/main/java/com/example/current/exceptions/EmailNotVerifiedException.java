package com.example.current.exceptions;

import org.springframework.http.HttpStatus;

public class EmailNotVerifiedException extends BaseException{
    public EmailNotVerifiedException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
