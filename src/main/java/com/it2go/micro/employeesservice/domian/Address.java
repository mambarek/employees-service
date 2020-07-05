package com.it2go.micro.employeesservice.domian;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @NotNull
    private UUID publicId;

    @Size(min = 3, max = 100)
    @NotNull
    private String streetOne;

    @Size(min = 1, max = 5)
    @NotNull
    private String buildingNr;

    @Size(min = 3, max = 100)
    private String streetTwo;

    @Size(min = 5, max = 10)
    @NotNull
    private String zipCode;

    @Size(min = 3, max = 100)
    @NotNull
    private String city;

    @Size(min = 2, max = 3)
    @NotNull
    private String countryCode;
}
