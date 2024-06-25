package com.example.current.exceptions;

public class EmailFailureException extends RuntimeException {
    public EmailFailureException(String message) {
        super(message);
    }
}
