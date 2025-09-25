package com.crediya.autenticacion.api.exceptions;

import com.crediya.autenticacion.model.ErrorMessage;
import com.crediya.autenticacion.model.constants.AuthConstants;
import com.crediya.autenticacion.model.exceptions.ValidationException;
import com.crediya.autenticacion.security.exceptions.CustomAuthenticationException;
import com.crediya.autenticacion.usecase.exceptions.ConflictException;
import com.crediya.autenticacion.usecase.exceptions.NotFoundException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebInputException;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        ErrorMessage errorMap;
        Throwable error = getError(request);

        switch (error) {
            case ValidationException exception -> {
                errorMap = ErrorMessage.builder()
                        .code(exception.getCode())
                        .message(error.getMessage())
                        .timestamp(new Date())
                        .build();
            }
            case ConflictException conflictException -> {
                errorMap = ErrorMessage.builder()
                        .code(conflictException.getCode())
                        .message(error.getMessage())
                        .timestamp(new Date())
                        .build();
            }
            case ServerWebInputException serverWebInputException -> {
                errorMap = ErrorMessage.builder()
                        .code(AuthConstants.ERROR_CODE_VALIDATION)
                        .message(AuthConstants.ERROR_MESSAGE_VALIDATION)
                        .timestamp(new Date())
                        .build();
            }
            case NotFoundException notFoundException -> {
                errorMap = ErrorMessage.builder()
                        .code(notFoundException.getCode())
                        .message(error.getMessage())
                        .timestamp(new Date())
                        .build();
            }
            case CustomAuthenticationException customAuthenticationException -> {
                errorMap = ErrorMessage.builder()
                        .code(customAuthenticationException.getCode())
                        .message(error.getMessage())
                        .timestamp(new Date())
                        .build();
            }
            case null, default -> {
                errorMap = ErrorMessage.builder()
                        .code(AuthConstants.ERROR_CODE_UNEXPECTED)
                        .message(AuthConstants.ERROR_MESSAGE_UNEXPECTED)
                        .timestamp(new Date())
                        .build();
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("code", errorMap.getCode());
        response.put("message", errorMap.getMessage());
        response.put("timestamp", errorMap.getTimestamp());
        return response;
    }
}
