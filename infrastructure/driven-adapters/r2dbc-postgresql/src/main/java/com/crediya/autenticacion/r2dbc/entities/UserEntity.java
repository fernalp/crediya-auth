package com.crediya.autenticacion.r2dbc.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Table("users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private BigInteger id;
    @Column("id_number")
    private String idNumber;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("birth_date")
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private BigDecimal salary;
    @Column("role_id")
    private Integer idRole;
    private String password;

}
