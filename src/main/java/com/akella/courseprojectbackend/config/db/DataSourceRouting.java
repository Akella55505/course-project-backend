package com.akella.courseprojectbackend.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.NonNull;

import javax.sql.DataSource;
import java.util.Map;

public final class DataSourceRouting extends AbstractRoutingDataSource {
    private Map<Object, Object> targetDataSources;

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.get();
    }

    @Override
    public void setTargetDataSources(@NonNull Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.targetDataSources = targetDataSources;
    }

    public void addDataSource(String key, DataSource dataSource) {
        this.targetDataSources.put(key, dataSource);
        super.afterPropertiesSet();
    }

    public void removeDataSource(String key) {
        this.targetDataSources.remove(key);
        super.afterPropertiesSet();
    }

    public boolean dataSourceExists(String key) {
        return this.targetDataSources.containsKey(key);
    }
}
