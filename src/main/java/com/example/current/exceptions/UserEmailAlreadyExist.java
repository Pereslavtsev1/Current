package com.example.current.exceptions;


import org.springframework.http.HttpStatus;

public class UserEmailAlreadyExist extends BaseException {
    public UserEmailAlreadyExist(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
