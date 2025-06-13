package com.asset.asset_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/", "/index.html", "/home", "/home.html").permitAll()
        .anyRequest().authenticated()
    )
    .formLogin(form -> form
        .loginPage("/index.html")           // 👈 Your static login page
        .loginProcessingUrl("/login")       // 👈 Form submits here (POST)
        .defaultSuccessUrl("/home", true)   // 👈 After login, redirect here
        .permitAll()
    )
    .logout(logout -> logout
        .logoutSuccessUrl("/index.html")
        .permitAll()
    );

    return http.build();
}


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("admin123")
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(admin);
    }
}
