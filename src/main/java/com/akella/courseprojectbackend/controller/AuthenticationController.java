package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.UserDto;
import com.akella.courseprojectbackend.security.AuthenticationResponse;
import com.akella.courseprojectbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserDto registrationData) {
        try {
            return ResponseEntity.ok(authenticationService.register(registrationData));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserDto loginData) {
        try {
            return ResponseEntity.ok(authenticationService.login(loginData));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
