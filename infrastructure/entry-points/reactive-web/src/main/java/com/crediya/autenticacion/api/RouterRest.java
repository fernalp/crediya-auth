package com.crediya.autenticacion.api;

import com.crediya.autenticacion.api.openapi.UserOpenApi;
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
                .POST("/api/v1/usuarios", handlerV1::createUser, UserOpenApi::createUser)
                .GET("/api/v1/usuarios/{idNumber}", handlerV1::findUserByIdNumber, UserOpenApi::findUserByIdNumber)
                .POST("/api/v1/login", handlerV1::loginUser, UserOpenApi::loginUser)
            .build();
        }
}
