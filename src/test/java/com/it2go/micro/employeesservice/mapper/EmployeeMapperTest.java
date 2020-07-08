package com.it2go.micro.employeesservice.mapper;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.util.EmployeesProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EmployeeMapperTest {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    void testEmployeeToEmployeeEntity(){
        Employee employee = EmployeesProducer.createEmployee();
        EmployeeEntity employeeEntity = employeeMapper.employeeToEmployeeEntity(employee);

        assertEquals(employeeEntity.getFirstName(), employee.getData().getFirstName());
    }

    @Test
    void testEmployeeEntityToEmployee(){
        EmployeeEntity employeeEntity = EmployeesProducer.createEmployeeEntity();
        Employee employee = employeeMapper.employeeEntityToEmployee(employeeEntity);

        assertEquals(employee.getData().getFirstName(), employeeEntity.getFirstName());
    }

    @Test
    void testUpdateEmployeeEntity(){
        EmployeeEntity employeeEntity = EmployeesProducer.createEmployeeEntity();
        System.out.println(employeeEntity);

        Employee employee = EmployeesProducer.createEmployee();
        System.out.println(employee);

        EmployeeEntity updatedEmployeeEntity = employeeMapper.updateEmployeeEntity(employeeEntity, employee);
        System.out.println(updatedEmployeeEntity);

        assertEquals(employee.getData().getFirstName(), updatedEmployeeEntity.getFirstName());
        assertEquals(employee.getPublicId(), updatedEmployeeEntity.getPublicId());
        assertNotNull(updatedEmployeeEntity.getId());
        assertNotNull(updatedEmployeeEntity.getAddress().getId());
    }


}
