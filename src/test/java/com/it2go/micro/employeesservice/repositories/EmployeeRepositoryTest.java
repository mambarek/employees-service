package com.it2go.micro.employeesservice.repositories;

import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void testCreate(){
        DocumentEntity doc1 = DocumentEntity.builder()
                .name("My life")
                .contentType("application/pdf")
                .build();

        DocumentEntity doc2 = DocumentEntity.builder()
                .name("The Universe")
                .contentType("application/pdf")
                .build();

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .publicId(UUID.randomUUID())
                .salary(1000.00)
                .traveling(true).build();

        employeeEntity.addDocument(doc1);
        employeeEntity.addDocument(doc2);

        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);

        Optional<EmployeeEntity> byId = employeeRepository.findById(savedEmployee.getId());
        EmployeeEntity byPublicId = employeeRepository.findByPublicId(employeeEntity.getPublicId());

        assertNotNull(savedEmployee.getId());
        assertEquals(employeeEntity.getPublicId(), savedEmployee.getPublicId());
        assertEquals(savedEmployee.getId(), byPublicId.getId());

        System.out.println("Hier warten ");
    }

}
