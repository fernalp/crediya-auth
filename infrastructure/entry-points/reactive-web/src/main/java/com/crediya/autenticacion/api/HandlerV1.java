package com.crediya.autenticacion.api;

import com.crediya.autenticacion.api.dtos.CreateUserDTO;
import com.crediya.autenticacion.api.mappers.UserMapper;
import com.crediya.autenticacion.api.validator.ReactiveValidator;
import com.crediya.autenticacion.usecase.createuser.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HandlerV1 {

    private final CreateUserUseCase createUserUseCase;
    private final ReactiveValidator reactiveValidator;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TransactionalOperator tx;

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDTO.class)
                .flatMap(reactiveValidator::validate)
                .map(UserMapper::toUser)
                .flatMap(createUserUseCase::execute)
                .map(UserMapper::toUserResponseDTO)
                .flatMap(userResponseDTO -> {
                    log.info("Usuario creado exitosamente {}", userResponseDTO.toString());
                    return ServerResponse
                            .status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(userResponseDTO);
                }).as(tx::transactional)
                .doOnError(error -> log.error(error.toString()))
                ;
}

}
