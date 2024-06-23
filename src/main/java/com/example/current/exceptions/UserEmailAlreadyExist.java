package com.example.current.exceptions;


import org.springframework.http.HttpStatus;

public class UserEmailAlreadyExist extends BaseException {
    public UserEmailAlreadyExist(String simpleResponse, HttpStatus httpStatus) {
        super(new SimpleResponse(simpleResponse), httpStatus);
    }
}
