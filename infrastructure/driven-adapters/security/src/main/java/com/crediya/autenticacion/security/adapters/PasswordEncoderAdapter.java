package com.crediya.autenticacion.security.adapters;

import com.crediya.autenticacion.model.user.gateways.PasswordEncoderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PasswordEncoderAdapter implements PasswordEncoderGateway {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<String> encode(CharSequence rawPassword) {
        return Mono.fromCallable(() -> passwordEncoder.encode(rawPassword));
    }

    @Override
    public Mono<Boolean> matches(CharSequence rawPassword, String encodedPassword) {
        return Mono.fromCallable(()-> passwordEncoder.matches(rawPassword, encodedPassword));
    }
}
