package com.example.current.api.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RegistrationBody(
        @NotBlank(message = "Username may not be blank")
        @Size(min = 4, message = "Username must be at least 4 characters long")
        String username,

        @NotBlank(message = "Password may not be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Size(max = 32, message = "Password must not exceed 32 characters")
        String password,

        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "First name may not be blank")
        @Size(min = 2, message = "First name must be at least 2 characters long")
        String firstName,

        @NotBlank(message = "Last name may not be blank")
        @Size(min = 2, message = "Last name must be at least 2 characters long")
        String lastName
) {

}
