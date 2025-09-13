package com.crediya.autenticacion.api.openapi;

import com.crediya.autenticacion.api.dtos.CreateUserDTO;
import com.crediya.autenticacion.api.dtos.UserResponseDTO;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

@UtilityClass
public class UserOpenApi {

    private static final String SUCCESS = "Usuario creado exitosamente";
    private static final String BAD_REQUEST = HttpStatus.BAD_REQUEST.getReasonPhrase();
    private static final String CONFLICT = HttpStatus.CONFLICT.getReasonPhrase();
    private static final String CREATED_CODE = String.valueOf(HttpStatus.CREATED.value());
    private static final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private static final String CONFLICT_CODE = String.valueOf(HttpStatus.CONFLICT.value());


    public Builder createUser(Builder builder) {
    return builder
            .operationId("savedUser")
            .description("Crear un nuevo usuario")
            .tag("Usuario")
            .requestBody(
                    requestBodyBuilder()
                            .required(true)
                            .content(
                                    contentBuilder()
                                            .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                            .schema(schemaBuilder().implementation(CreateUserDTO.class))
                            )
            ).response(
                    responseBuilder()
                            .responseCode(CREATED_CODE)
                            .description(SUCCESS)
                            .content(
                                    contentBuilder()
                                            .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                            .schema(schemaBuilder().implementation(UserResponseDTO.class))
                            )
            ).response(
                    responseBuilder()
                            .responseCode(BAD_REQUEST_CODE)
                            .description(BAD_REQUEST)
            ).response(
                    responseBuilder()
                            .responseCode(CONFLICT_CODE)
                            .description(CONFLICT)
            )
            ;
    }

}
