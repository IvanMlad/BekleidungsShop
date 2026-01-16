package com.shop.backend.controller;

import com.shop.backend.model.User;
import com.shop.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // DTOs (kleine Hilfsklassen)
    public static class RegisterRequest {
        public String username;
        public String password;
        public String role;
    }

    public static class LoginRequest {
        public String username;
        public String password;
    }

    public static class AuthResponse {
        public String username;
        public String role;

        public AuthResponse(String username, String role) {
            this.username = username;
            this.role = role;
        }
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req) {
        if (req.username == null || req.username.isBlank() || req.password == null || req.password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username und Passwort sind erforderlich.");
        }

        if (userRepository.existsByUsername(req.username.trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username existiert bereits.");
        }

        String role = (req.role == null || req.role.isBlank()) ? "CUSTOMER" : req.role;

        // Demo: Passwort im Klartext (für Praktikum ok; in echt: Hashing!)
        User user = new User(req.username.trim(), req.password, role.trim().toUpperCase());
        userRepository.save(user);

        return new AuthResponse(user.getUsername(), user.getRole());
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        if (req.username == null || req.username.isBlank() || req.password == null || req.password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username und Passwort sind erforderlich.");
        }

        User user = userRepository.findByUsername(req.username.trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login fehlgeschlagen."));

        if (!user.getPassword().equals(req.password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login fehlgeschlagen.");
        }

        return new AuthResponse(user.getUsername(), user.getRole());
    }
}

