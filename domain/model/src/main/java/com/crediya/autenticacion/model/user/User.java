package com.crediya.autenticacion.model.user;
import com.crediya.autenticacion.model.role.Role;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private BigInteger id;
    private String idNumber;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String address;
    private String phone;
    private String email;
    private BigDecimal salary;
    private Role role;

}
