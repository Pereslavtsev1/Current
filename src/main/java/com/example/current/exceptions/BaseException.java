package com.example.current.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final SimpleResponse simpleResponse;
    private final HttpStatus httpStatus;

    public BaseException(SimpleResponse simpleResponse, HttpStatus httpStatus) {
        super(simpleResponse.getMessage());
        this.simpleResponse = simpleResponse;
        this.httpStatus = httpStatus;
    }
}
