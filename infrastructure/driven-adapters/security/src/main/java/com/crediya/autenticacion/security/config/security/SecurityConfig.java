package com.crediya.autenticacion.security.config.security;

import com.crediya.autenticacion.security.config.jwt.JwtAuthenticationFilter;
import com.crediya.autenticacion.security.exceptions.CustomAccessDeniedException;
import com.crediya.autenticacion.security.exceptions.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/api/doc/swagger-ui.html",
            "/api/doc/api-docs/**",
            "/api/doc/swagger-ui/**",
            "/docs",
            "/scalar/**"
    };

    private final CustomAccessDeniedException customAccessDeniedException;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .exceptionHandling(
                        exceptionHandlingSpec -> exceptionHandlingSpec
                                .accessDeniedHandler(customAccessDeniedException)
                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(AUTH_WHITELIST).permitAll()
                        .pathMatchers("/api/v1/login").permitAll()
                        .anyExchange().authenticated()

                ).addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}
