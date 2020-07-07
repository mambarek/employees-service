package com.it2go.micro.employeesservice.services;

import com.it2go.micro.employeesservice.domian.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeesService {

    Employee findEmployeeByPublicId(UUID publicId);
    Employee saveNewEmployee(Employee employee);
    Employee updateEmployee(Employee employee);
    Long countEmployees();
}
