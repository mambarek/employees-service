package com.it2go.micro.employeesservice.domian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private UUID publicId;

    @NotNull
    @Size(min = 3, max = 100)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 100)
    private String lastName;

    private LocalDate birthDate;
}
