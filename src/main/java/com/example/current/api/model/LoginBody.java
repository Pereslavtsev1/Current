package com.example.current.api.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LoginBody{
        @NotBlank(message = "Username may not be blank")
        @Size(min = 4, message = "Username must be at least 4 characters long")
        @NotNull
        private String username;
        @NotNull
        @NotBlank(message = "Password may not be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Size(max = 32, message = "Password must not exceed 32 characters")
        private String password;
}
