package com.it2go.micro.employeesservice.services;

import com.it2go.micro.employeesservice.domian.Employee;

import java.util.UUID;

public interface EmployeesService {

    Employee findEmployeeByPublicId(UUID publicId);

    void saveNewEmployee(Employee employee);
}
