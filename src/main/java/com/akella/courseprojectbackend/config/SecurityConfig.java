package com.akella.courseprojectbackend.config;

import com.akella.courseprojectbackend.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/logout").authenticated()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/accidents/user").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST, "/accidents").hasAuthority("POLICE")
                        .requestMatchers("/accidents/report").hasAuthority("POLICE")
                        .requestMatchers("/accidents/statistics/**").hasAuthority("POLICE")
                        .requestMatchers("/accidents/**").hasAnyAuthority("ADMIN", "COURT", "POLICE", "INSURANCE", "MEDIC")
                        .requestMatchers(HttpMethod.PATCH, "/persons").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST,"/persons").hasAuthority("POLICE")
                        .requestMatchers("/persons/{id}").hasAuthority("POLICE")
                        .requestMatchers(HttpMethod.PATCH, "/persons").hasAuthority("USER")
                        .requestMatchers("/persons/**").hasAnyAuthority("ADMIN", "COURT", "POLICE", "INSURANCE", "MEDIC")
                        .requestMatchers(HttpMethod.POST, "/vehicles").hasAuthority("POLICE")
                        .requestMatchers("/vehicles/**").hasAnyAuthority("ADMIN", "COURT", "POLICE", "INSURANCE")
                        .requestMatchers("/court-decisions").hasAuthority("COURT")
                        .requestMatchers("/insurance/**").hasAuthority("INSURANCE")
                        .requestMatchers("/medics").hasAuthority("MEDIC")
                        .requestMatchers("/medical-reports").hasAuthority("MEDIC")
                        .requestMatchers("/policemen").hasAuthority("POLICE")
                        .requestMatchers("/administrative-decisions").hasAuthority("POLICE")
                        .requestMatchers("/violations").hasAuthority("POLICE")
                        .requestMatchers("/applications/{id}").hasAuthority("POLICE")
                        .requestMatchers(HttpMethod.POST, "/applications").hasAnyAuthority("USER")
                        .requestMatchers(HttpMethod.GET, "/applications").hasAnyAuthority("ADMIN", "POLICE")
                        .requestMatchers("/admin").hasAnyAuthority("ADMIN")
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                        .accessDeniedHandler(jwtAccessDeniedHandler())
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(
                "http://localhost:*",
                "https://localhost:*",
                "http://127.0.0.1:*",
                "https://*.ngrok.io",
                "https://*.ngrok-free.app",
                "https://*.ngrok.app"
        ));

        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization", "Content-Type", "X-Requested-With"
        ));

        configuration.setExposedHeaders(List.of(
                "Authorization"
        ));

        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(
                    "{\"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\"}"
            );
        };
    }

    @Bean
    public AccessDeniedHandler jwtAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(
                    "{\"error\": \"Access Denied\", \"message\": \"" + accessDeniedException.getMessage() + "\"}"
            );
        };
    }
}
