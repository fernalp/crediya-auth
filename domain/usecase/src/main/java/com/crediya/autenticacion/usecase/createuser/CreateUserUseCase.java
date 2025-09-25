package com.crediya.autenticacion.usecase.createuser;

import com.crediya.autenticacion.model.constants.AuthConstants;
import com.crediya.autenticacion.model.role.gateways.RoleRepository;
import com.crediya.autenticacion.model.user.gateways.PasswordEncoderGateway;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.model.user.validations.UserValidation;
import com.crediya.autenticacion.usecase.exceptions.ConflictException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoderGateway passwordEncoderGateway;

    public Mono<User> execute(User user) {
        return roleRepository.findByName(AuthConstants.DEFAULT_ROLE)
                .map(role -> {
                            user.setRole(role);
                            return user;
                        })
                .flatMap(UserValidation::validate)
                .flatMap(this::validateExistEmailAndIdNumber)
                .flatMap(this::encodePassword)
                .flatMap(userRepository::save);
    }

    private Mono<User> validateExistEmailAndIdNumber(User user) {
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if(exists) {
                        return Mono.error(new ConflictException(AuthConstants.ERROR_CODE_CONFLICT_USER, AuthConstants.ERROR_MESSAGE_EMAIL_EXISTS));
                    }
                    return userRepository.existsByIdNumber(user.getIdNumber());
                })
                .flatMap(exists -> {
                    if(exists) {
                        return Mono.error(new ConflictException(AuthConstants.ERROR_CODE_CONFLICT_USER, AuthConstants.ERROR_MESSAGE_ID_NUMBER_EXISTS));
                    }
                    return Mono.just(user);
                });
    }

    private Mono<User> encodePassword(User user) {
        return Mono.just(user)
                .flatMap( userP -> passwordEncoderGateway.encode(userP.getPassword())
                        .map(encodedPassword -> {
                            userP.setPassword(encodedPassword);
                            return userP;
                        }));
    }

}
