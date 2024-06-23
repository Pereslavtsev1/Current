package com.example.current.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<SimpleResponse> handleBaseException(BaseException ex){
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getSimpleResponse());
    }
}
