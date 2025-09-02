package com.crediya.autenticacion.model.user;
import com.crediya.autenticacion.model.role.Role;
import com.crediya.autenticacion.model.user.valueObjects.Email;
import com.crediya.autenticacion.model.user.valueObjects.Salary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {

    private BigInteger id;
    private String idNumber;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String address;
    private String phone;
    private Email email;
    private Salary salary;
    private Role role;

    public User(BigInteger id, String idNumber, String firstname, String lastname, LocalDate birthdate, String address, String phone, Email email, Salary salary, Role role) {
        this.id = id;
        this.idNumber = idNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.role = role;
    }

    public User(BigInteger id, String idNumber, String firstname, String lastname, LocalDate birthdate, String address, String phone, String email, BigDecimal salary, Role role) {
        this.id = id;
        this.idNumber = idNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.address = address;
        this.phone = phone;
        this.email = Email.create(email);
        this.salary = Salary.create(salary);
        this.role = role;
    }

}
