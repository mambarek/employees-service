package com.it2go.micro.employeesservice.web.controller;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.services.EmployeesService;
import com.it2go.micro.employeesservice.services.EmployeesServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    private final EmployeesService employeesService;

    @PostMapping
    public ResponseEntity<Employee> saveNewEmployee(@RequestBody @Valid Employee employee){
        Employee savedEmployee = employeesService.saveNewEmployee(employee);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{publicId}")
                .buildAndExpand(employee.getPublicId()).toUri();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uri);

        return new ResponseEntity<>(savedEmployee, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{publicId}")
    public ResponseEntity<Employee> updateEmploy(@RequestBody @Valid Employee employee){
        Employee updateEmployee = employeesService.updateEmployee(employee);

        return ResponseEntity.ok(updateEmployee);
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<Employee> findEmployeeByPublicId(@PathVariable("publicId") @NotNull UUID publicId) {
        Employee employeeByPublicId = employeesService.findEmployeeByPublicId(publicId);

        return new ResponseEntity<>(employeeByPublicId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> findAllEmployees(){
        List<Employee> allEmployees = employeesService.findAllEmployees();

        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getEmployeeCount(){
        return  new ResponseEntity<>(employeesService.countEmployees(), HttpStatus.OK);
    }
}
