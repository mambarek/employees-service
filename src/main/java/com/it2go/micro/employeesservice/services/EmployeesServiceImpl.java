package com.it2go.micro.employeesservice.services;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmployeesService{

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee findEmployeeByPublicId(UUID publicId){
        return employeeMapper.employeeEntityToEmployee(employeeRepository.findByPublicId(publicId));
    }

    @Override
    public void saveNewEmployee(Employee employee) {
        employeeRepository.save(employeeMapper.employeeToEmployeeEntity(employee));
    }
}
