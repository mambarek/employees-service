package com.it2go.micro.employeesservice.search.table;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class EmployeeTableItem implements Serializable {

  private UUID publicId;
  private String firstName;
  private String lastName;
  private LocalDate birthDate;
  private Double salary;
  private boolean traveling;
  private boolean weekendWork;
  private OffsetDateTime createdAt;

}

