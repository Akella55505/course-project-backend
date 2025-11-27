package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.config.db.DataSourceRouting;
import com.akella.courseprojectbackend.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
@RequiredArgsConstructor
public class AdminService {
    private DataSourceRouting dataSourceRouting;

    @Autowired
    public AdminService(DataSourceRouting dataSourceRouting) {
        this.dataSourceRouting = dataSourceRouting;
    }

    public void updateUserRole(String email, Role role) throws SQLException {
        try (Connection connection = dataSourceRouting.getConnection();
             CallableStatement call = connection.prepareCall("{ call auth.grant_role(?, ?) }")) {

            call.setString(1, role.getDatabaseRole());
            call.setString(2, email);

            call.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SQLException();
        }
    }
}
