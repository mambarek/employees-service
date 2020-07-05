package com.it2go.micro.employeesservice.domian;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends FullMonitoredEntity{

    // to get this object from any database relational or NoSQl, use this key in the queries
    @NotNull
    private UUID publicId;

    private PersonData data;

    private Double salary;
    private boolean weekendWork;
    private boolean traveling;
    private List<Document> documents = new ArrayList<>();
}
