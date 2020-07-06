package com.it2go.micro.employeesservice.repositories;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.persistence.jpa.entities.AddressEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
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
        DocumentEntity doc1 = DocumentEntity.builder()
                .name("My life")
                .contentType("application/pdf")
                .build();

        DocumentEntity doc2 = DocumentEntity.builder()
                .name("The Universe")
                .contentType("application/pdf")
                .build();

        AddressEntity addressEntity = AddressEntity.builder()
                .buildingNr("55")
                .city("Mannheim")
                .countryCode("DE")
                .publicId(UUID.randomUUID())
                .streetOne("Bahnhofstr.")
                .zipCode("12345")
                .build();

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .publicId(UUID.randomUUID())
                .firstName("Martin")
                .lastName("Fowler")
                .birthDate(LocalDate.parse("1975-05-10"))
                .salary(2000.0)
                .traveling(true)
                .weekendWork(false)
                .email("martin.fowler@google.com")
                .gender("MALE")
                .documents(new ArrayList<>())
                .address(addressEntity)
                .build();

        employeeEntity.addDocument(doc1);
        employeeEntity.addDocument(doc2);

        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);

        Optional<EmployeeEntity> byId = employeeRepository.findById(savedEmployee.getId());
        System.out.println(" >>>   ById is executed: " );
        byId.ifPresent(entity -> {
            List<DocumentEntity> documents = entity.getDocuments();
            Employee employee = employeeMapper.employeeEntityToEmployee(entity);
            System.out.println(employee);
        });


        EmployeeEntity byPublicId = employeeRepository.findByPublicId(employeeEntity.getPublicId());

        assertNotNull(savedEmployee.getId());
        assertEquals(employeeEntity.getPublicId(), savedEmployee.getPublicId());
        assertEquals(savedEmployee.getId(), byPublicId.getId());

        System.out.println("Hier warten ");
    }

}
