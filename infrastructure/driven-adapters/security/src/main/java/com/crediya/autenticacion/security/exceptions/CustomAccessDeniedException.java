package com.crediya.autenticacion.security.exceptions;

import com.crediya.autenticacion.model.ErrorMessage;
import com.crediya.autenticacion.model.constants.AuthConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAccessDeniedException implements ServerAccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorMessage data = ErrorMessage
                .builder()
                .code(AuthConstants.ERROR_CODE_FORBIDDEN)
                .message(AuthConstants.ERROR_MESSAGE_FORBIDDEN)
                .timestamp(new Date())
                .build();

        log.error("{}: {}", data.getCode(), data);

        byte[] responseBody;
        try {
            responseBody = objectMapper.writeValueAsString(data).getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            return Mono.error(new CustomAuthenticationException(AuthConstants.ERROR_MESSAGE_FORBIDDEN));
        }

        return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBody)));
    }
}
