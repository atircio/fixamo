package com.fixmystreet.fixmystreet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class RequestConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/api/reports/**").permitAll()
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-ui/index.html"
                ).permitAll()
                // All other endpoints require authentication
                .anyRequest().authenticated()
        )
                .httpBasic(withDefaults());// or use .formLogin(), or JWT setup

        return http.build();
    }


}
