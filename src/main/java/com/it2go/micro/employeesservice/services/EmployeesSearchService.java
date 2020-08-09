package com.it2go.micro.employeesservice.services;

import com.it2go.micro.employeesservice.search.table.EmployeeTableItem;
import com.it2go.micro.employeesservice.search.table.EmployeesSearchTemplate;
import java.util.List;

public interface EmployeesSearchService {
  public List<EmployeeTableItem> filterEmployees(EmployeesSearchTemplate employeesSearchTemplate);
}
