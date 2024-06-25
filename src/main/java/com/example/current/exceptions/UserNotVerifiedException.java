package com.example.current.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserNotVerifiedException extends RuntimeException {
    private final boolean newEmailSent;
}
