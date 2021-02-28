package com.it2go.micro.employeesservice.services;

import com.it2go.micro.employeesservice.domian.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeesService {

    List<Employee> findAllEmployees();
    Employee findEmployeeByPublicId(UUID publicId) throws EntityNotFoundException;
    Employee saveNewEmployee(Employee employee);
    Employee updateEmployee(Employee employee);
    void deleteEmployee(UUID publicId);
    Long countEmployees();
}
