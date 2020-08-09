package com.it2go.micro.employeesservice.services;

import com.it2go.micro.employeesservice.search.table.EmployeesSearchTemplate;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItemList;

public interface EmployeesSearchService {
  public EmployeeTableItemList filterEmployees(EmployeesSearchTemplate employeesSearchTemplate);
}
