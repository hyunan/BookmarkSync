package com.hyunan.bookmarkmanager.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyunan.bookmarkmanager.dto.UserDTO;
import com.hyunan.bookmarkmanager.entity.User;
import com.hyunan.bookmarkmanager.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody(required = true) UserDTO user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(user.getPassword());
        User newUser = new User(user.getUsername(), hashedPassword);
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("username", newUser.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody(required = true) UserDTO user, HttpSession session) {
        var testUser = userRepository.findByUsername(user.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (testUser.isPresent() && encoder.matches(user.getPassword(), testUser.get().getPasswordHash())) {
            session.setAttribute("user_id", testUser.get().getId());
            return ResponseEntity.ok(Map.of("username", testUser.get().getUsername()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
    }
}
