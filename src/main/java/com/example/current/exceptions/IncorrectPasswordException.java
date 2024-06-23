package com.example.current.exceptions;

import org.springframework.http.HttpStatus;

public class IncorrectPasswordException extends BaseException{
    public IncorrectPasswordException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
