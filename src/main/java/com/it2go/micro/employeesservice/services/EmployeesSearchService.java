package com.it2go.micro.employeesservice.services;

import com.it2go.micro.employeesservice.search.table.EmployeeTableItemList;
import com.it2go.util.jpa.search.SearchTemplate;

public interface EmployeesSearchService {
  public void test();
  EmployeeTableItemList filterEmployees(SearchTemplate searchTemplate);
}
