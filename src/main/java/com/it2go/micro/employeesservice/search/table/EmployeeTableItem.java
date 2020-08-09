package com.it2go.micro.employeesservice.search.table;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@XmlRootElement
public class EmployeeTableItem implements Serializable {
  private Long id;
  private String firstName;
  private String lastName;
  private Date birthDate;
  private Double salary;
  private boolean traveling;
  private boolean weekendWork;
  private String createdByName;
  private Date createdAt;

  public EmployeeTableItem() {
  }
}

