package com.crediya.autenticacion.api.openapi;

import com.crediya.autenticacion.api.constants.ApiConstants;
import com.crediya.autenticacion.api.dtos.CreateUserDTO;
import com.crediya.autenticacion.api.dtos.LoginDTO;
import com.crediya.autenticacion.api.dtos.UserResponseDTO;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.MediaType;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;
import static org.springdoc.core.fn.builders.securityrequirement.Builder.securityRequirementBuilder;

@UtilityClass
public class UserOpenApi {

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
                            .responseCode(ApiConstants.HTTP_STATUS_CODE_CREATED)
                            .description(ApiConstants.SUCCESS_CREATED)
                            .content(
                                    contentBuilder()
                                            .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                            .schema(schemaBuilder().implementation(UserResponseDTO.class))
                            )
            ).response(
                    responseBuilder()
                            .responseCode(ApiConstants.HTTP_STATUS_CODE_BAD_REQUEST)
                            .description(ApiConstants.BAD_REQUEST)
            ).response(
                    responseBuilder()
                            .responseCode(ApiConstants.HTTP_STATUS_CODE_CONFLICT)
                            .description(ApiConstants.CONFLICT)
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
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_OK)
                                .description(ApiConstants.SUCCESS_FOUND)
                                .content(
                                        contentBuilder()
                                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                                .schema(schemaBuilder().implementation(UserResponseDTO.class))
                                )
                ).response(
                        responseBuilder()
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_NOT_FOUND)
                                .description(ApiConstants.NOT_FOUND)
                )
                .response(
                        responseBuilder()
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_BAD_REQUEST)
                                .description(ApiConstants.BAD_REQUEST)
                )
                .response(
                        responseBuilder()
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_CONFLICT)
                                .description(ApiConstants.CONFLICT)
                )
                .response(
                        responseBuilder()
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_UNAUTHORIZED)
                                .description(ApiConstants.UNAUTHORIZED)
                )
                .response(
                        responseBuilder()
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_FORBIDDEN)
                                .description(ApiConstants.FORBIDDEN)
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
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_OK)
                                .description(ApiConstants.LOGIN_SUCCESS)
                                .content(
                                        contentBuilder()
                                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                                .schema(schemaBuilder().implementation(UserResponseDTO.class))
                                )
                ).response(
                        responseBuilder()
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_NOT_FOUND)
                                .description(ApiConstants.NOT_FOUND)
                ).response(
                        responseBuilder()
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_BAD_REQUEST)
                                .description(ApiConstants.BAD_REQUEST)
                );
    }

    public Builder findAll(Builder builder){
        return builder
                .operationId("findAllUser")
                .description("Listar todos los usuarios")
                .tag("Usuario")
                .security(
                        securityRequirementBuilder()
                                .name("bearerAuth")
                ).response(
                        responseBuilder()
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_OK)
                                .description(ApiConstants.SUCCESS_FOUND)
                                .content(
                                        contentBuilder()
                                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                                .schema(schemaBuilder().implementation(UserResponseDTO.class))
                                )
                )
                .response(
                        responseBuilder()
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_UNAUTHORIZED)
                                .description(ApiConstants.UNAUTHORIZED)
                )
                .response(
                        responseBuilder()
                                .responseCode(ApiConstants.HTTP_STATUS_CODE_FORBIDDEN)
                                .description(ApiConstants.FORBIDDEN)
                );
    }
}
