package com.crediya.autenticacion.model.role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
public class Role {
    private Integer id;
    private String name;
    private String description;

    public Role(Integer id, String name, String description) {
        this.id = id;
        this.name = name.trim().toUpperCase();
        this.description = description.trim().toLowerCase();
    }

}
