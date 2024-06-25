package com.example.current.api.contollers.auth;

import com.example.current.api.model.LoginBody;
import com.example.current.api.model.LoginResponse;
import com.example.current.api.model.RegistrationBody;
import com.example.current.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Long> register(@Valid @RequestBody RegistrationBody registrationBody) {
        return ResponseEntity.ok(userService.register(registrationBody));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginBody loginBody) {
        var loginResponse = userService.login(loginBody);
            return ResponseEntity.ok(loginResponse);

    }
    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token) {
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
