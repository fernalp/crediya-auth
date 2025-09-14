package com.crediya.autenticacion.usecase.finduserbyidnumber;

import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.usecase.exceptions.UserNotFound;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindUserByIdNumberUseCase {

    private static final String ERROR_MESSAGE = "El usuario no fue encontrado";

    private final UserRepository userRepository;

    public Mono<User> execute(String idNumber) {
        return userRepository.findByIdNumber(idNumber)
                .switchIfEmpty(Mono.error(new UserNotFound(ERROR_MESSAGE)));
    }

}
