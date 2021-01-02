package com.it2go.micro.employeesservice.events;

import com.it2go.micro.employeesservice.domian.Employee;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by mmbarek on 01.01.2021.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeExportEvent implements Serializable {

  static final long serialVersionUID = -1176371081847413397L;

  private List<Employee> employees;

}
