package com.crediya.autenticacion.usecase.loginuser;

import com.crediya.autenticacion.model.constants.AuthConstants;
import com.crediya.autenticacion.model.exceptions.ValidationException;
import com.crediya.autenticacion.model.role.gateways.RoleRepository;
import com.crediya.autenticacion.model.token.Token;
import com.crediya.autenticacion.model.token.gateways.AuthRepository;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.model.user.gateways.PasswordEncoderGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUserUseCase {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoderGateway passwordEncoder;

    public Mono<Token> execute(String email, String password) {
        return this.validateUser(email, password)
                .flatMap(this::validateRole)
                        .flatMap(user -> authRepository.login(email, password, user));
    }

    private Mono<User> validateUser(String email, String password) {
        if(email == null || password == null) {
            return Mono.error(new ValidationException(AuthConstants.ERROR_CODE_VALIDATION, AuthConstants.ERROR_MESSAGE_USER_OR_PASS_INCORRECT));
        }
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new ValidationException(AuthConstants.ERROR_CODE_VALIDATION, AuthConstants.ERROR_MESSAGE_USER_NOT_FOUND)))
                .flatMap(user -> passwordEncoder
                                .matches(password, user.getPassword())
                                .flatMap(isValid -> {
                                    if(isValid) {
                                        return Mono.just(user);
                                    }
                                    return Mono.error(new ValidationException(AuthConstants.ERROR_CODE_VALIDATION, AuthConstants.ERROR_MESSAGE_USER_OR_PASS_INCORRECT));
                                })
                );
    }

    private Mono<User> validateRole(User user) {
        return roleRepository.findById(user.getRole().getId())
                .flatMap(role -> {
                    user.setRole(role);
                    return Mono.just(user);
                });
    }

}
