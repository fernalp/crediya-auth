package com.crediya.autenticacion.security.config.jwt;

import com.crediya.autenticacion.model.constants.AuthConstants;
import com.crediya.autenticacion.security.exceptions.CustomAuthenticationException;
import com.crediya.autenticacion.security.services.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(AuthConstants.AUTHORIZATION_HEADER);
        if (authHeader != null && authHeader.startsWith(AuthConstants.BEARER_PREFIX)) {
            String token = Objects.requireNonNull(authHeader).substring(AuthConstants.BEARER_PREFIX_LENGTH);
            return jwtProvider.extractClaims(token)
                    .map(claims -> {
                        String username = claims.getSubject();
                        String role = claims.get(AuthConstants.CLAIMS_ROLE, String.class);
                        var authorities = List.of(new SimpleGrantedAuthority(AuthConstants.AUTHORITY_PREFIX + role));
                        return new UsernamePasswordAuthenticationToken(username, null, authorities);
                    })
                    .flatMap(authentication -> chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)))
                    .doOnSuccess(v -> log.info("JWT authentication successful"))
                    .onErrorResume(e -> {
                        log.error("JWT authentication failed", e.getMessage());
                        return Mono.error(new CustomAuthenticationException(AuthConstants.ERROR_MESSAGE_UNAUTHORIZED));
                    });
        }
        return chain.filter(exchange);
    }
}
