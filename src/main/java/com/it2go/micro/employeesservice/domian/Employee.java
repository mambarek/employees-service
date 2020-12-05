package com.it2go.micro.employeesservice.domian;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    // to get this object from any database relational or NoSQl, use this key in the queries
    @NotNull
    private UUID publicId;
    @NotNull
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @NotNull
    private UUID createdBy;
    private UUID updatedBy;

    @NotNull
    private PersonData data;

    private Double salary;
    private boolean weekendWork;
    private boolean traveling;

    private List<Document> documents = new ArrayList<>();
}
