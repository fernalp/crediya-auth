package com.crediya.autenticacion.api.exceptions;

import com.crediya.autenticacion.model.exceptions.ValidationException;
import com.crediya.autenticacion.usecase.exceptions.ConflictException;
import com.crediya.autenticacion.usecase.exceptions.NotFoundException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebInputException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorMap = new HashMap<>();
        Throwable error = getError(request);

        switch (error) {
            case ValidationException exception -> {
                errorMap.put("code", exception.getCode());
                errorMap.put("message", error.getMessage());
                errorMap.put("timestamp", LocalDateTime.now());
            }
            case ConflictException conflictException -> {
                errorMap.put("code", conflictException.getCode());
                errorMap.put("message", error.getMessage());
                errorMap.put("timestamp", LocalDateTime.now());
            }
            case ServerWebInputException serverWebInputException -> {
                errorMap.put("code", "ERROR_VALIDATION");
                errorMap.put("message", "No podemos procesar la solicitud, verifica los datos proporcionados");
                errorMap.put("timestamp", LocalDateTime.now());
            }
            case NotFoundException notFoundException -> {
                errorMap.put("code", notFoundException.getCode());
                errorMap.put("message", error.getMessage());
                errorMap.put("timestamp", LocalDateTime.now());
            }
            case null, default -> {
                errorMap.put("code", "ERROR_UNEXPECTED");
                errorMap.put("message", "Ocurrió un error inesperado");
                errorMap.put("timestamp", LocalDateTime.now());
            }
        }

        return errorMap;
    }
}
