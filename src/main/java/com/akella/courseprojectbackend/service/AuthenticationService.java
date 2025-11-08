package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.dto.UserDto;
import com.akella.courseprojectbackend.enums.Role;
import com.akella.courseprojectbackend.exception.UserAlreadyExistsException;
import com.akella.courseprojectbackend.model.User;
import com.akella.courseprojectbackend.security.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final DataSource dataSource;

    public AuthenticationResponse register(UserDto registrationData) throws SQLException {
        boolean registered;

        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(true);
        try (CallableStatement call = connection.prepareCall("{ call auth.register_user(?, ?, ?) }")) {

            call.setString(1, registrationData.getEmail());
            call.setString(2, passwordEncoder.encode(registrationData.getPassword()));
            call.registerOutParameter(3, Types.BOOLEAN);

            call.execute();
            registered = call.getBoolean(3);
        }

        if (!registered) throw new UserAlreadyExistsException("User with this email already exists");

        var token = jwtService.generateToken(
                User.builder().email(registrationData.getEmail()).password(registrationData.getPassword()).role(Role.USER).build()
        );

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse login(UserDto loginData) throws SQLException {
        boolean authenticated;
        Role role;

        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(true);
        try (CallableStatement call = connection.prepareCall("{ call auth.authenticate_user(?, ?, ?, ?) }")) {

            call.setString(1, loginData.getEmail());
            call.setString(2, loginData.getPassword());
            call.registerOutParameter(3, Types.BOOLEAN);
            call.registerOutParameter(4, Types.VARCHAR);

            call.execute();
            authenticated = call.getBoolean(3);
            role = Role.valueOf(call.getString(4));
        }

        if (!authenticated) throw new BadCredentialsException("Invalid credentials");

        var token = jwtService.generateToken(
                User.builder().email(loginData.getEmail()).password(loginData.getPassword()).role(role).build()
        );

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
