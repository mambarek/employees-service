package com.it2go.micro.employeesservice.domian;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class MonitoredEntity {

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
