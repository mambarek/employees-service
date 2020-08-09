package com.it2go.micro.employeesservice.web.controller;

import com.it2go.micro.employeesservice.search.table.EmployeeTableItem;
import com.it2go.micro.employeesservice.search.table.EmployeesSearchTemplate;
import com.it2go.micro.employeesservice.services.EmployeesSearchService;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees/search")
@RequiredArgsConstructor
public class EmployeesSearchController {
  private final EmployeesSearchService employeesSearchService;

  @GetMapping("/")
  public ResponseEntity<List<EmployeeTableItem>> searchEmployees(@RequestBody @NotNull EmployeesSearchTemplate employeesSearchTemplate){
    List<EmployeeTableItem> employeeTableItems = employeesSearchService
        .filterEmployees(employeesSearchTemplate);

    return ResponseEntity.ok(employeeTableItems);
  }
}
