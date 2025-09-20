package com.crediya.autenticacion.model.token.gateways;

import com.crediya.autenticacion.model.token.Token;
import com.crediya.autenticacion.model.user.User;
import reactor.core.publisher.Mono;

public interface AuthRepository {

    Mono<Token> login(String email, String password, User user);

}
