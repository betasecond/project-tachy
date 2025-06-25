package edu.jimei.projecttachy.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//-TODO: Replace with actual DTOs
record LoginRequest(String email, String password) {}
record LoginResponse(String token) {}

@RestController
@RequestMapping("/api")
public class AuthController {

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        // TODO: Implement real authentication logic with Spring Security
        System.out.println("Login attempt for user: " + loginRequest.email());
        // Return a dummy token for now to satisfy the frontend
        return new LoginResponse("dummy-jwt-token-for-" + loginRequest.email());
    }
} 