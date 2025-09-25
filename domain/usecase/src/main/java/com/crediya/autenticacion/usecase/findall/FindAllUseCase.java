package com.crediya.autenticacion.usecase.findall;

import com.crediya.autenticacion.model.user.User;
import com.crediya.autenticacion.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FindAllUseCase {

    private final UserRepository userRepository;

    public Flux<User> execute() {
        return userRepository.findAll();
    }

}
