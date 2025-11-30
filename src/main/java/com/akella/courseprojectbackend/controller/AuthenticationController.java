package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.ApplicationUtils;
import com.akella.courseprojectbackend.dto.UserDto;
import com.akella.courseprojectbackend.enums.Role;
import com.akella.courseprojectbackend.security.AuthenticationResponse;
import com.akella.courseprojectbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto registrationData) {
        try {
            authenticationService.register(registrationData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody UserDto loginData) {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.login(loginData);
            ResponseCookie responseCookie = ResponseCookie.from("Token", authenticationResponse.getToken())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(86400)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE,
                    responseCookie.toString()).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logoutUser() {
        try {
            authenticationService.logout(ApplicationUtils.getEmailFromContext());
            ResponseCookie responseCookie = ResponseCookie.from("Token", "")
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(0)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE,
                    responseCookie.toString()).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/role")
    public ResponseEntity<Map<String, Role>> getUserRole() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("role", ApplicationUtils.getRoleFromContext()));
    }
}
