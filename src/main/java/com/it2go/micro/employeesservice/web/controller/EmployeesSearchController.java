package com.it2go.micro.employeesservice.web.controller;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItem;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItemList;
import com.it2go.micro.employeesservice.services.EmployeesSearchService;
import com.it2go.micro.employeesservice.services.EmployeesService;
import com.it2go.util.jpa.search.SearchTemplate;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees/search")
@RequiredArgsConstructor
public class EmployeesSearchController {
  private final EmployeesService employeesService;
  private final EmployeesSearchService employeesSearchService;

  @GetMapping
  public ResponseEntity<List<Employee>> findAllEmployees(){
    List<Employee> allEmployees = employeesService.findAllEmployees();

    return new ResponseEntity<>(allEmployees, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<EmployeeTableItemList> searchEmployees(@RequestBody @NotNull SearchTemplate searchTemplate){
    EmployeeTableItemList employeeTableItemList = employeesSearchService
        .filterEmployees(searchTemplate);

    return ResponseEntity.ok(employeeTableItemList);
  }
}
