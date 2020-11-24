package com.it2go.micro.employeesservice.services.impl;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import com.it2go.micro.employeesservice.services.EmployeesService;
import com.it2go.micro.employeesservice.services.EntityNotFoundException;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmployeesService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAllEmployees() {
        List<Employee> result = new ArrayList<>();
        employeeRepository.findAll().forEach(
            employeeEntity -> {
                result.add(employeeMapper.employeeEntityToEmployee(employeeEntity));
            }
        );

        return result;
    }

    @Override
    public Employee findEmployeeByPublicId(UUID publicId){
        EmployeeEntity employeeEntity = employeeRepository.findByPublicId(publicId).orElse(null);
        return employeeMapper.employeeEntityToEmployee(employeeEntity);
    }

    @Override
    public Employee saveNewEmployee(Employee employee) {
        employee.setCreatedAt(OffsetDateTime.now());
        employee.setCreatedBy(UUID.randomUUID()); // TODO to be changed with user publicId
        EmployeeEntity employeeEntity = employeeRepository.save(employeeMapper.employeeToEmployeeEntity(employee));

        return employeeMapper.employeeEntityToEmployee(employeeEntity);
    }

    @Override
    public Employee updateEmployee(Employee employee){
        EmployeeEntity byPublicId = employeeRepository.findByPublicId(employee.getPublicId())
                .orElseThrow(EntityNotFoundException::new);

        EmployeeEntity employeeEntity = employeeMapper.updateEmployeeEntity(byPublicId, employee);
        employeeEntity.setUpdatedAt(OffsetDateTime.now());
        employeeEntity.setUpdatedBy(UUID.randomUUID()); // TODO to be changed with user publicId

        EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);

        return employeeMapper.employeeEntityToEmployee(savedEntity);
    }

    @Override
    public void deleteEmploy(UUID publicId) {
        EmployeeEntity byPublicId = employeeRepository.findByPublicId(publicId)
            .orElseThrow(EntityNotFoundException::new);

        employeeRepository.delete(byPublicId);
    }

    @Override
    public Long countEmployees() {
        return employeeRepository.count();
    }
}
