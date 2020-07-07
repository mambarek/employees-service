package com.it2go.micro.employeesservice.services;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmployeesService{

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee findEmployeeByPublicId(UUID publicId){
        EmployeeEntity employeeEntity = employeeRepository.findByPublicId(publicId).orElseThrow(EntityNotFoundException::new);
        return employeeMapper.employeeEntityToEmployee(employeeEntity);
    }

    @Override
    public Employee saveNewEmployee(Employee employee) {
        EmployeeEntity employeeEntity = employeeRepository.save(employeeMapper.employeeToEmployeeEntity(employee));

        return employeeMapper.employeeEntityToEmployee(employeeEntity);
    }

    @Override
    public Employee updateEmployee(Employee employee){
        EmployeeEntity byPublicId = employeeRepository.findByPublicId(employee.getPublicId())
                .orElseThrow(EntityNotFoundException::new);

        EmployeeEntity employeeEntity = employeeMapper.updateEmployeeEntity(byPublicId, employee);

        EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);

        return employeeMapper.employeeEntityToEmployee(savedEntity);
    }

    @Override
    public Long countEmployees() {
        return employeeRepository.count();
    }
}
