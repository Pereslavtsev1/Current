package com.example.current.exceptions;


import org.springframework.http.HttpStatus;

public class UsernameNotFoundException extends BaseException{
    public UsernameNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
