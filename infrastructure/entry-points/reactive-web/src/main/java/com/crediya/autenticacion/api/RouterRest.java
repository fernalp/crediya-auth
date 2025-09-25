package com.crediya.autenticacion.api;

import com.crediya.autenticacion.api.openapi.UserOpenApi;
import com.crediya.autenticacion.model.constants.AuthConstants;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;


@Configuration
public class RouterRest {

    @Bean
    public WebProperties.Resources resources(){
        return new WebProperties.Resources();
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(HandlerV1 handlerV1) {
        return route()
                .POST(AuthConstants.API_PATH_USERS, handlerV1::createUser, UserOpenApi::createUser)
                .GET(AuthConstants.API_PATH_USERS, handlerV1::findAllUsers, UserOpenApi::findAll)
                .GET(AuthConstants.API_PATH_USERS + "/{idNumber}", handlerV1::findUserByIdNumber, UserOpenApi::findUserByIdNumber)
                .POST(AuthConstants.API_PATH_LOGIN, handlerV1::loginUser, UserOpenApi::loginUser)
            .build();
        }
}
