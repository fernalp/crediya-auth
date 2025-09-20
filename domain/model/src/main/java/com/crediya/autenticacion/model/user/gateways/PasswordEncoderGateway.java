package com.crediya.autenticacion.model.user.gateways;

import reactor.core.publisher.Mono;

public interface PasswordEncoderGateway {
    Mono<String> encode(CharSequence rawPassword);
    Mono<Boolean> matches(CharSequence rawPassword, String encodedPassword);
}
