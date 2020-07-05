package com.it2go.micro.employeesservice.web.controller;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.services.EmployeesService;
import com.it2go.micro.employeesservice.services.EmployeesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeesController {

    private final EmployeesService employeesService;

    @PostMapping
    public ResponseEntity<Void> saveNewEmployee(@RequestBody @Validated Employee employee){
        employeesService.saveNewEmployee(employee);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{publicId}")
                .buildAndExpand(employee.getPublicId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{publicId}")
    public Employee findEmployeeByPublicId(@PathVariable("publicId") UUID publicId) {
        return employeesService.findEmployeeByPublicId(publicId);
    }
}
