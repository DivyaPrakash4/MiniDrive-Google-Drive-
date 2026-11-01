package com.miniDrive.MiniDrive.controller;

import com.miniDrive.MiniDrive.dto.LoginRequest;
import com.miniDrive.MiniDrive.dto.RegisterRequest;
import com.miniDrive.MiniDrive.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
