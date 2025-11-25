package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.config.db.DataSourceContextHolder;
import com.akella.courseprojectbackend.config.db.DataSourceRouting;
import com.akella.courseprojectbackend.dto.UserDto;
import com.akella.courseprojectbackend.enums.Role;
import com.akella.courseprojectbackend.model.auth.User;
import com.akella.courseprojectbackend.security.AuthenticationResponse;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.akella.courseprojectbackend.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final Environment environment;
    private final JwtService jwtService;
    private final DataSourceRouting dataSourceRouting;

    public void register(UserDto registrationData) throws SQLException {
        boolean registered;

        Connection connection = dataSourceRouting.getConnection();
        connection.setAutoCommit(true);
        try (CallableStatement call = connection.prepareCall("{ call auth.register_user(?, ?, ?) }")) {

            call.setString(1, registrationData.getEmail());
            call.setString(2, registrationData.getPassword());
            call.registerOutParameter(3, Types.BOOLEAN);

            call.execute();
            registered = call.getBoolean(3);
        }

        if (!registered) throw new UserAlreadyExistsException("User with this email already exists");
    }

    public AuthenticationResponse login(UserDto loginData) throws SQLException {
        Role role;
        Connection connection = null;
        // Credential check
        try (Connection ignored = DriverManager.getConnection(
                Objects.requireNonNull(environment.getProperty("spring.datasource.url")),
                loginData.getEmail(), loginData.getPassword())) {
            if (!dataSourceRouting.dataSourceExists(loginData.getEmail())) {
                HikariConfig config = new HikariConfig();
                config.setJdbcUrl(environment.getProperty("spring.datasource.url"));
                config.setUsername(loginData.getEmail());
                config.setPassword(loginData.getPassword());
                config.setMaximumPoolSize(1);
                HikariDataSource dataSource = new HikariDataSource(config);
                dataSourceRouting.addDataSource(loginData.getEmail(), dataSource);
            }
            DataSourceContextHolder.set(loginData.getEmail());
            connection = dataSourceRouting.getConnection();

            CallableStatement call = connection.prepareCall("{ call auth.get_role(?) }");
            call.registerOutParameter(1, Types.VARCHAR);

            call.execute();
            role = Role.valueOf(call.getString(1).split("_")[0].toUpperCase());
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid credentials");
        } finally {
            if (connection != null) connection.close();
        }

        User user = User.builder().email(loginData.getEmail()).password(loginData.getPassword()).role(role).build();

        return AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
    }

    public void logout(String email) {
        dataSourceRouting.removeDataSource(email);
    }
}
