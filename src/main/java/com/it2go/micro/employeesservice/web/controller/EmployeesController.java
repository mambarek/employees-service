package com.it2go.micro.employeesservice.web.controller;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.services.EmployeesService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeesController {

  private final Environment env;

  private final EmployeesService employeesService;

  /**
   * This is only for testing configuration server
   */
  @GetMapping("/message")
  public ResponseEntity<String> getMessage() {
    return new ResponseEntity<>( env.getProperty("test.config.server.message"), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Employee> saveNewEmployee(@Valid @RequestBody Employee employee) {
    Employee savedEmployee = employeesService.saveNewEmployee(employee);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{publicId}")
        .buildAndExpand(employee.getPublicId()).toUri();

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(uri);

    return new ResponseEntity<>(savedEmployee, responseHeaders, HttpStatus.CREATED);
  }

  @PutMapping("/{publicId}")
  public ResponseEntity<Employee> updateEmploy(@RequestBody @Valid Employee employee,
      @PathVariable @NotNull UUID publicId) {
    if (!publicId.equals(employee.getPublicId())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Employee updateEmployee = employeesService.updateEmployee(employee);

    return ResponseEntity.ok(updateEmployee);
  }

  @GetMapping("/{publicId}")
  public ResponseEntity<Employee> findEmployeeByPublicId(
      @PathVariable("publicId") @NotNull UUID publicId) {
    Employee employeeByPublicId = employeesService.findEmployeeByPublicId(publicId);

    if (employeeByPublicId == null) {
      return ResponseEntity.notFound().build();
    }

    return new ResponseEntity<>(employeeByPublicId, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Employee>> findAllEmployees() {
    List<Employee> allEmployees = employeesService.findAllEmployees();

    return new ResponseEntity<>(allEmployees, HttpStatus.OK);
  }

  @DeleteMapping("/{publicId}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable @NotNull UUID publicId) {
    employeesService.deleteEmployee(publicId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/count")
  public ResponseEntity<Long> getEmployeeCount() {
    return new ResponseEntity<>(employeesService.countEmployees(), HttpStatus.OK);
  }
}
