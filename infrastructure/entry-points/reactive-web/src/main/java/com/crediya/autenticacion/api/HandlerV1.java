package com.crediya.autenticacion.api;

import com.crediya.autenticacion.api.dtos.CreateUserDTO;
import com.crediya.autenticacion.api.dtos.LoginDTO;
import com.crediya.autenticacion.api.mappers.UserMapper;
import com.crediya.autenticacion.api.validator.ReactiveValidator;
import com.crediya.autenticacion.usecase.createuser.CreateUserUseCase;
import com.crediya.autenticacion.usecase.findall.FindAllUseCase;
import com.crediya.autenticacion.usecase.finduserbyidnumber.FindUserByIdNumberUseCase;
import com.crediya.autenticacion.usecase.loginuser.LoginUserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class HandlerV1 {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdNumberUseCase findUserByIdNumberUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final FindAllUseCase findAllUsersUseCase;
    private final ReactiveValidator reactiveValidator;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TransactionalOperator tx;

    @PreAuthorize("hasAnyRole('ADMIN', 'ADVISOR')")
    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateUserDTO.class)
                .flatMap(reactiveValidator::validate)
                .map(UserMapper::toUser)
                .flatMap(createUserUseCase::execute)
                .as(tx::transactional)
                .map(UserMapper::toUserResponseDTO)
                .flatMap(userResponseDTO -> {
                    log.info("Usuario creado exitosamente {}", userResponseDTO.toString());
                    return ServerResponse
                            .status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(userResponseDTO);
                })
                .doOnError(error -> log.error(error.toString()))
                ;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ADVISOR')")
    public Mono<ServerResponse> findUserByIdNumber(ServerRequest serverRequest) {
        String idNumber = serverRequest.pathVariable("idNumber");
        return findUserByIdNumberUseCase.execute(idNumber)
                .as(tx::transactional)
                .map(UserMapper::toUserResponseDTO)
                .flatMap(userResponseDTO -> {
                    log.info("Usuario encontrado exitosamente {}", userResponseDTO.toString());
                    return ServerResponse
                            .status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(userResponseDTO);
                })
                .doOnError(error -> log.error(error.toString()));
    }

    public Mono<ServerResponse> loginUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoginDTO.class)
                .flatMap(reactiveValidator::validate)
                .flatMap( loginDTO -> loginUserUseCase.execute(loginDTO.correoElectronico(), loginDTO.contrasenia()))
                .flatMap( token -> {
                    log.info("Usuario logueado exitosamente {}", token.toString());
                    return ServerResponse
                            .status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(token);
                })
                .doOnError(error -> log.error(error.getMessage()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ADVISOR')")
    public Mono<ServerResponse> findAllUsers(ServerRequest serverRequest) {
        return findAllUsersUseCase.execute()
                .as(tx::transactional)
                .map(UserMapper::toUserResponseDTO)
                .collectList()
                .flatMap(userResponseDTO -> {
                    log.info("Usuarios encontrado exitosamente {}", userResponseDTO.toString());
                    return ServerResponse
                            .status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(userResponseDTO);
                })
                .doOnError(error -> log.error(error.getMessage()));
    }

}
