package com.asset.asset_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 🔴 Important for form login from static HTML
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.html", "/login", "/login.html", "/image/**").permitAll()
                .anyRequest().permitAll() // 🔴 Allow all other requests too
            )
            .formLogin(form -> form.disable()) // 🔴 Disable Spring Security’s default login form
            .logout(logout -> logout
                .logoutSuccessUrl("/index.html").permitAll()
            );

        return http.build();
    }
}
