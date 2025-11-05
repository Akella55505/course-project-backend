package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.dto.UserDto;
import com.akella.courseprojectbackend.enums.Role;
import com.akella.courseprojectbackend.model.User;
import com.akella.courseprojectbackend.repository.UserRepository;
import com.akella.courseprojectbackend.security.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserDto registrationData) {
        User user = User.builder()
                .email(registrationData.getEmail())
                .password(passwordEncoder.encode(registrationData.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse login(UserDto loginData) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getEmail(),
                loginData.getPassword()));
        User user = userRepository.findByEmail(loginData.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
