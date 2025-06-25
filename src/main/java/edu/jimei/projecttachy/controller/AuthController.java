package edu.jimei.projecttachy.controller;

import edu.jimei.projecttachy.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

record LoginRequest(String email, String password) {}
record LoginResponse(String token) {}
record ErrorResponse(String message) {}

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("Login attempt for user: " + loginRequest.email());
            
            String token = authService.authenticate(loginRequest.email(), loginRequest.password());
            
            System.out.println("Login successful for user: " + loginRequest.email());
            return ResponseEntity.ok(new LoginResponse(token));
            
        } catch (RuntimeException e) {
            System.out.println("Login failed for user: " + loginRequest.email() + ", reason: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }
} 