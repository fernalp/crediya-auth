package com.crediya.autenticacion.security.config.security;

import com.crediya.autenticacion.model.constants.AuthConstants;
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
                        .pathMatchers(AuthConstants.AUTH_WHITELIST).permitAll()
                        .pathMatchers(AuthConstants.API_PATH_LOGIN).permitAll()
                        .anyExchange().authenticated()

                ).addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}
