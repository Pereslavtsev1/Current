package com.example.current.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExist extends BaseException {
    public UsernameAlreadyExist(String simpleResponse, HttpStatus httpStatus) {
        super(new SimpleResponse(simpleResponse), httpStatus);
    }
}
