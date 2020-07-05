package com.it2go.micro.employeesservice.domian;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullMonitoredEntity extends MonitoredEntity{
    private User createdBy;
    private User updatedBy;
}
