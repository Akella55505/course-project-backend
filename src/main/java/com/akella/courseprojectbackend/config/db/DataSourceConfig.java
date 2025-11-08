package com.akella.courseprojectbackend.config.db;

import com.akella.courseprojectbackend.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSourceRouter routingDataSource = new DataSourceRouter();

        DataSource userDataSource = DataSourceBuilder.create()
                .url(environment.getProperty("spring.datasource.url"))
                .username(environment.getProperty("user.datasource.username"))
                .password(environment.getProperty("user.datasource.password"))
                .build();

        DataSource adminDataSource = DataSourceBuilder.create()
                .url(environment.getProperty("spring.datasource.url"))
                .username(environment.getProperty("admin.datasource.username"))
                .password(environment.getProperty("admin.datasource.password"))
                .build();

        DataSource courtDataSource = DataSourceBuilder.create()
                .url(environment.getProperty("spring.datasource.url"))
                .username(environment.getProperty("court.datasource.username"))
                .password(environment.getProperty("court.datasource.password"))
                .build();

        DataSource policeDataSource = DataSourceBuilder.create()
                .url(environment.getProperty("spring.datasource.url"))
                .username(environment.getProperty("police.datasource.username"))
                .password(environment.getProperty("police.datasource.password"))
                .build();

        DataSource insuranceDataSource = DataSourceBuilder.create()
                .url(environment.getProperty("spring.datasource.url"))
                .username(environment.getProperty("insurance.datasource.username"))
                .password(environment.getProperty("insurance.datasource.password"))
                .build();

        DataSource medicDataSource = DataSourceBuilder.create()
                .url(environment.getProperty("spring.datasource.url"))
                .username(environment.getProperty("medic.datasource.username"))
                .password(environment.getProperty("medic.datasource.password"))
                .build();

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put(Role.USER, userDataSource);
        dataSources.put(Role.ADMIN, adminDataSource);
        dataSources.put(Role.POLICE, policeDataSource);
        dataSources.put(Role.MEDIC, medicDataSource);
        dataSources.put(Role.INSURANCE, insuranceDataSource);
        dataSources.put(Role.COURT, courtDataSource);

        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(userDataSource);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }
}
