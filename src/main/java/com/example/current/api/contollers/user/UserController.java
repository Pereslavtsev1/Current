package com.example.current.api.contollers.user;

import com.example.current.model.LocalUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/secured")
public class UserController {
    @GetMapping("/me")
    public ResponseEntity<LocalUser> getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
        return ResponseEntity.ok(user);
    }
}
