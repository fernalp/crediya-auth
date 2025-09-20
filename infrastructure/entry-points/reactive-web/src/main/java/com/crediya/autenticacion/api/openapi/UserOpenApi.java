package com.crediya.autenticacion.api.openapi;

import com.crediya.autenticacion.api.dtos.CreateUserDTO;
import com.crediya.autenticacion.api.dtos.LoginDTO;
import com.crediya.autenticacion.api.dtos.UserResponseDTO;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;
import static org.springdoc.core.fn.builders.securityrequirement.Builder.securityRequirementBuilder;

@UtilityClass
public class UserOpenApi {

    private static final String NOT_FOUND = HttpStatus.NOT_FOUND.getReasonPhrase();
    private static final String SUCCESS_CREATED = "Usuario creado exitosamente";
    private static final String SUCCESS_FOUND = "Usuario encontrado exitosamente";
    private static final String BAD_REQUEST = HttpStatus.BAD_REQUEST.getReasonPhrase();
    private static final String CONFLICT = HttpStatus.CONFLICT.getReasonPhrase();
    private static final String CREATED_CODE = String.valueOf(HttpStatus.CREATED.value());
    private static final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private static final String CONFLICT_CODE = String.valueOf(HttpStatus.CONFLICT.value());
    private static final String SUCCESS_CODE = String.valueOf(HttpStatus.OK.value());
    private static final String NOT_FOUND_CODE = String.valueOf(HttpStatus.NOT_FOUND.value());


    public Builder createUser(Builder builder) {
    return builder
            .operationId("savedUser")
            .description("Crear un nuevo usuario")
            .tag("Usuario")
            .security(
                    securityRequirementBuilder()
                            .name("bearerAuth")
            )
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
                            .description(SUCCESS_CREATED)
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

    public Builder findUserByIdNumber(Builder builder) {
        return builder
            .operationId("findByIdNumber")
                .description("Buscar un usuario por su número de identificación")
                .tag("Usuario")
                .parameter(
                        parameterBuilder()
                                .name("idNumber")
                                .description("Número de identificación")
                                .required(true)
                                .in(ParameterIn.PATH)
                                .schema(schemaBuilder().implementation(String.class))
                )
                .security(
                        securityRequirementBuilder()
                                .name("bearerAuth")
                )
                .response(
                        responseBuilder()
                                .responseCode(SUCCESS_CODE)
                                .description(SUCCESS_FOUND)
                                .content(
                                        contentBuilder()
                                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                                .schema(schemaBuilder().implementation(UserResponseDTO.class))
                                )
                ).response(
                        responseBuilder()
                                .responseCode(NOT_FOUND_CODE)
                                .description(NOT_FOUND)
                )
        ;
    }

    public Builder loginUser(Builder builder) {
        return builder
                .operationId("loginUser")
                .description("Inicio de sesion de un usuario")
                .tag("Auth")
                .requestBody(
                        requestBodyBuilder()
                                .required(true)
                                .content(
                                        contentBuilder()
                                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                                .schema(schemaBuilder().implementation(LoginDTO.class))
                                        )
                ).response(
                        responseBuilder()
                                .responseCode(SUCCESS_CODE)
                                .description(SUCCESS_FOUND)
                                .content(
                                        contentBuilder()
                                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                                .schema(schemaBuilder().implementation(UserResponseDTO.class))
                                )
                ).response(
                        responseBuilder()
                                .responseCode(NOT_FOUND_CODE)
                                .description(NOT_FOUND)
                );
    }
}
