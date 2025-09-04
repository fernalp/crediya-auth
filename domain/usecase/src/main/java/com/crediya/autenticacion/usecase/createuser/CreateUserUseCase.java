package com.crediya.autenticacion.usecase.createuser;

import com.crediya.autenticacion.model.role.gateways.RoleRepository;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.model.user.validations.UserValidation;
import com.crediya.autenticacion.usecase.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateUserUseCase {

    private static final String DEFAULT_ROLE = "CUSTOMER";
    private static final String ERROR_CODE = "CONFLICT_USER";
    private static final String ERROR_MESSAGE_ID_NUMBER = "El número de identificación ya existe";
    private static final String ERROR_MESSAGE_EMAIL = "El correo electrónico ya existe";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public Mono<User> execute(User user) {
        return roleRepository.findByName(DEFAULT_ROLE)
                .map(role -> {
                            user.setRole(role);
                            return user;
                        })
                .flatMap(UserValidation::validate)
                .flatMap(this::validateExistEmailAndIdNumber)
                .flatMap(userRepository::save);
    }

    private Mono<User> validateExistEmailAndIdNumber(User user) {
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if(exists) {
                        return Mono.error(new ConflictException(ERROR_CODE, ERROR_MESSAGE_EMAIL));
                    }
                    return userRepository.existsByIdNumber(user.getIdNumber());
                })
                .flatMap(exists -> {
                    if(exists) {
                        return Mono.error(new ConflictException(ERROR_CODE, ERROR_MESSAGE_ID_NUMBER));
                    }
                    return Mono.just(user);
                });
    }

}
