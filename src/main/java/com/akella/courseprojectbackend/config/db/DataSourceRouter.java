package com.akella.courseprojectbackend.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public final class DataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.get();
    }
}
