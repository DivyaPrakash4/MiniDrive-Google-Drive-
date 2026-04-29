package com.miniDrive.MiniDrive.service;

import com.miniDrive.MiniDrive.dto.LoginRequest;
import com.miniDrive.MiniDrive.dto.RegisterRequest;
import com.miniDrive.MiniDrive.entity.User;
import com.miniDrive.MiniDrive.repository.UserRepository;
import com.miniDrive.MiniDrive.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    /* =========================
       REGISTER USER
       ========================= */
    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    /* =========================
       LOGIN USER
       ========================= */
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
