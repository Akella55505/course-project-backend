package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.config.db.DataSourceContextHolder;
import com.akella.courseprojectbackend.config.db.DataSourceRouting;
import com.akella.courseprojectbackend.dto.UserDto;
import com.akella.courseprojectbackend.enums.Role;
import com.akella.courseprojectbackend.model.auth.User;
import com.akella.courseprojectbackend.security.AuthenticationResponse;
import org.springframework.boot.jdbc.DataSourceBuilder;
import com.akella.courseprojectbackend.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final Environment environment;
    private final JwtService jwtService;
    private final DataSource dataSource;
    private final DataSourceRouting dataSourceRouting;

    public void register(UserDto registrationData) throws SQLException {
        boolean registered;

        Connection connection = dataSource.getConnection();
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

    public AuthenticationResponse login(UserDto loginData) {
        Role role;
        Connection connection;
        try {
            if (!dataSourceRouting.dataSourceExists(loginData.getEmail())) {
                DataSource dataSource = DataSourceBuilder.create()
                        .driverClassName(environment.getProperty("spring.datasource.driver-class-name"))
                        .url(environment.getProperty("spring.datasource.url"))
                        .username(loginData.getEmail())
                        .password(loginData.getPassword())
                        .build();

                dataSourceRouting.addDataSource(loginData.getEmail(), dataSource);

                connection = dataSource.getConnection();
            } else {
                DataSourceContextHolder.set(loginData.getEmail());
                connection = dataSource.getConnection();
            }

            CallableStatement call = connection.prepareCall("{ call auth.get_role(?) }");
            call.registerOutParameter(1, Types.VARCHAR);

            call.execute();
            role = Role.valueOf(call.getString(1).split("_")[0].toUpperCase());
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid credentials");
        }

        User user = User.builder().email(loginData.getEmail()).password(loginData.getPassword()).role(role).build();

        return AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
    }
}
