package com.it2go.micro.employeesservice.repositories;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.persistence.jpa.entities.AddressEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import com.it2go.micro.employeesservice.services.EntityNotFoundException;
import com.it2go.micro.employeesservice.util.EmployeesProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    @Transactional
    // @Transactional ensures that all of the method calls in that test method happens within same boundary.
    // Problem was LazyInitializationException: failed to lazily initialize a collection of role "documents"
    void testCreate(){

        EmployeeEntity employeeEntity = EmployeesProducer.createEmployeeEntity();
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);

        Optional<EmployeeEntity> byId = employeeRepository.findById(savedEmployee.getId());
        byId.ifPresent(entity -> {
            List<DocumentEntity> documents = entity.getDocuments();
            Employee employee = employeeMapper.employeeEntityToEmployee(entity);
            System.out.println(employee);
        });

        assertNotNull(savedEmployee.getId());
        assertEquals(employeeEntity.getPublicId(), savedEmployee.getPublicId());
    }

    @Test
    @Transactional
    void testUpdate(){
        Employee employee = EmployeesProducer.createEmployee();
        EmployeeEntity employeeEntity = employeeMapper.employeeToEmployeeEntity(employee);
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);

        EmployeeEntity loadedEntity = employeeRepository.findByPublicId(employeeEntity.getPublicId())
                .orElseThrow(EntityNotFoundException::new);

        Employee employee2 = EmployeesProducer.createEmployee2();
        EmployeeEntity updateEmployeeEntity = employeeMapper.updateEmployeeEntity(loadedEntity, employee2);

        EmployeeEntity save = employeeRepository.save(updateEmployeeEntity);
        System.out.println(save);
    }
}
