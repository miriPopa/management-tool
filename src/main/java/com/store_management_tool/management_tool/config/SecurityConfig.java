package com.store_management_tool.management_tool.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthConvertor jwtAuthConverter = new JwtAuthConvertor();

    @Value("${flywayApp.cors.origins}")
    private String corsOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(
                Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
                        "Access-Control-Request-Method", "Access-Control-Request-Headers",
                        "Origin", "Cache-Control", "Content-Type", "Authorization"));
        configuration.setAllowedOriginPatterns(Arrays.asList(corsOrigins.split(",")));
        configuration.addAllowedMethod("*"); // Allow all HTTP methods
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection as you have it now
                .csrf(csrf -> csrf.disable())

                // Enable CORS
                .cors(withDefaults())

                // Permit access to Swagger-related endpoints
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**",
                                "/swagger-ui.html", "/v1/login").permitAll()  // Allow public access to Swagger
                        .anyRequest().authenticated() // Require authentication for other endpoints
                )

                // Configure JWT authentication for OAuth2 Resource Server
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )

                // Set session management to stateless
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        return http.build();
    }

}