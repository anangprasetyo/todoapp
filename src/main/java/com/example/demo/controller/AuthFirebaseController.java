package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.example.demo.service.FirebaseAuthService;

@RestController
@RequestMapping("/api/auth/firebase")
public class AuthFirebaseController {

    @Autowired
    private FirebaseAuthService firebaseAuthService;

    @PostMapping("/signup")
    public LoginResponse signup(@RequestBody LoginRequest signupRequest) {
        return firebaseAuthService.signup(signupRequest);
    }

    @PostMapping("/login")
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            return firebaseAuthService.login(loginRequest);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid email or password");
        }

    }

    @PostMapping("/logout")
    public String logoutUser() {
        SecurityContextHolder.clearContext();
        return "User logged out successfully";
    }
}