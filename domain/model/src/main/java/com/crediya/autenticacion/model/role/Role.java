package com.crediya.autenticacion.model.role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Role {
    private Integer id;
    private String name;
    private String description;
}
