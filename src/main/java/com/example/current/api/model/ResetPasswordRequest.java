package com.example.current.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ResetPasswordRequest {
    @NotBlank
    @NotNull
    private String token;
    @NotNull
    @NotBlank(message = "Password may not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Size(max = 32, message = "Password must not exceed 32 characters")
    private String password;
}


