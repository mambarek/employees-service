package com.it2go.micro.employeesservice.domian;

import com.it2go.micro.employeesservice.masterdata.Gender;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonData {

    @NotNull
    @Size(min = 3, max = 100)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 100)
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Gender gender;

    @NotNull
    @Email
    private String email;

    private Address address;

}
