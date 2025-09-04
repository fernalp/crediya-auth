package com.crediya.autenticacion.api.exceptions;

import com.crediya.autenticacion.model.exceptions.ValidationException;
import com.crediya.autenticacion.usecase.exceptions.ConflictException;
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
        if (error instanceof ValidationException){
            errorMap.put("code", ((ValidationException) error).getCode());
            errorMap.put("message", error.getMessage());
            errorMap.put("timestamp", LocalDateTime.now());
        }
        if (error instanceof ConflictException){
            errorMap.put("code", ((ConflictException) error).getCode());
            errorMap.put("message", error.getMessage());
            errorMap.put("timestamp", LocalDateTime.now());
        }
        if (error instanceof ServerWebInputException){
            errorMap.put("code", "ERROR_VALIDATION");
            errorMap.put("message", "No podemos procesar la solicitud, verifica los datos proporcionados");
            errorMap.put("timestamp", LocalDateTime.now());
        }
        return errorMap;
    }
}
