package com.crediya.autenticacion.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ErrorMessage {
    private String code;
    private String message;
    private Date timestamp;
}
