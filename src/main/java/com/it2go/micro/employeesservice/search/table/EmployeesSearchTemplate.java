package com.it2go.micro.employeesservice.search.table;

import com.it2go.micro.employeesservice.search.Group;
import lombok.Data;

@Data
public class EmployeesSearchTemplate {

  private EmployeeTableItem employeeTableItem = new EmployeeTableItem();
  private String orderBy;
  private String orderDirection = "asc";
  private int maxResult = -1;
  private int offset = 0;
  private String searchField;
  private String searchString;
  private String searchOperation;
  private Group filters;
}
