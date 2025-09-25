package com.crediya.autenticacion.usecase.finduserbyidnumber;

import com.crediya.autenticacion.model.constants.AuthConstants;
import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import com.crediya.autenticacion.usecase.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindUserByIdNumberUseCase {

    private final UserRepository userRepository;

    public Mono<User> execute(String idNumber) {
        return userRepository.findByIdNumber(idNumber)
                .switchIfEmpty(Mono.error(new NotFoundException(AuthConstants.ERROR_MESSAGE_USER_NOT_FOUND)));
    }

}
