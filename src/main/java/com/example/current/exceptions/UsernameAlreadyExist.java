package com.example.current.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExist extends BaseException {
    public UsernameAlreadyExist(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
