package com.akella.courseprojectbackend.config.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    @Bean
    @Primary
    public DataSourceRouting dataSourceRouting() {
        DataSourceRouting routingDataSource = new DataSourceRouting();

        DataSource defaultDataSource = DataSourceBuilder.create()
                .url(environment.getProperty("spring.datasource.url"))
                .username(environment.getProperty("basic.datasource.username"))
                .password(environment.getProperty("basic.datasource.password"))
                .build();

        Map<Object, Object> targetDataSources = new ConcurrentHashMap<>();
        targetDataSources.put("default", defaultDataSource);
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(defaultDataSource);

        return routingDataSource;
    }
}
