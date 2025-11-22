package com.akella.courseprojectbackend.config;

import com.akella.courseprojectbackend.config.db.DataSourceInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final DataSourceInterceptor dataSourceInterceptor;

    public WebConfig(DataSourceInterceptor dataSourceInterceptor) {
        this.dataSourceInterceptor = dataSourceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dataSourceInterceptor).addPathPatterns("/**").excludePathPatterns("/auth/**");
    }
}
