package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.UserDto;
import com.akella.courseprojectbackend.exception.IncorrectPasswordException;
import com.akella.courseprojectbackend.repository.UserRepository;
import com.akella.courseprojectbackend.security.AuthenticationResponse;
import com.akella.courseprojectbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserDto registrationData) {
        if (userRepository.findByEmail(registrationData.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        return ResponseEntity.ok(authenticationService.register(registrationData));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserDto loginData) {
        if (userRepository.findByEmail(loginData.getEmail()).isEmpty()) {
            throw new UsernameNotFoundException("User with this email doesn't exist");
        }
        try {
            return ResponseEntity.ok(authenticationService.login(loginData));
        } catch (Exception e) {
            throw new IncorrectPasswordException("Incorrect password");
        }

    }
}
