package com.crediya.autenticacion.api;

import com.crediya.autenticacion.api.dtos.CreateUserDTO;
import com.crediya.autenticacion.api.mappers.UserMapper;
import com.crediya.autenticacion.usecase.createuser.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HandlerV1 {

    private final CreateUserUseCase createUserUseCase;

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDTO.class)
                .map(UserMapper::toUser)
                .flatMap(createUserUseCase::execute)
                .map(UserMapper::toUserResponseDTO)
                .flatMap(userResponseDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponseDTO));
}

}
