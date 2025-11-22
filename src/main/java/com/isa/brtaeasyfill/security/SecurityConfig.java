package com.isa.brtaeasyfill.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/", "/api/auth/**").permitAll()
                        .requestMatchers("/", "/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/**").authenticated()
                        .requestMatchers("/api/institutes/**").hasRole("SUPERADMIN")
                        .requestMatchers("/api/license/**").hasRole("SUPERADMIN")
                        .requestMatchers("/api/users/**").hasAnyRole("USER","SUPERADMIN")
                        .requestMatchers("/api/forms/**").hasAnyRole("USER", "SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/forms/*/pdf/a4").hasAnyRole("USER", "SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/forms/*/pdf/stamp").hasAnyRole("USER", "SUPERADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}