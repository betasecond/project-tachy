package edu.jimei.projecttachy.controller;

import edu.jimei.projecttachy.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

record LoginRequest(String email, String password) {}
record LoginResponse(String token) {}
record RegisterRequest(String name, String email, String password) {}
record RegisterResponse(String message, Long userId) {}
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            System.out.println("Register attempt for user: " + registerRequest.email());
            
            edu.jimei.projecttachy.entity.User user = authService.register(
                registerRequest.name(),
                registerRequest.email(), 
                registerRequest.password()
            );
            
            System.out.println("Registration successful for user: " + registerRequest.email());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new RegisterResponse("注册成功", user.getId()));
                    
        } catch (RuntimeException e) {
            System.out.println("Registration failed for user: " + registerRequest.email() + ", reason: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }
} 