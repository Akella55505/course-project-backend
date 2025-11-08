package com.akella.courseprojectbackend.config.db;

import com.akella.courseprojectbackend.enums.Role;
import com.akella.courseprojectbackend.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class DataSourceInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    public DataSourceInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        Role role = Role.valueOf(jwtService.extractRole(jwt));
        DataSourceContextHolder.set(role);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        DataSourceContextHolder.clear();
    }
}
