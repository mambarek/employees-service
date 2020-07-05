package com.it2go.micro.employeesservice.mapper;

import com.it2go.micro.employeesservice.domian.Address;
import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.domian.PersonData;
import com.it2go.micro.employeesservice.masterdata.Gender;
import com.it2go.micro.employeesservice.persistence.jpa.entities.AddressEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@SpringBootTest
class EmployeeMapperTest {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    void testEmployeeToEmployeeEntity(){
        Document doc1 = Document.builder()
                .name("My life")
                .contentType("application/pdf")
                .build();

        Document doc2 = Document.builder()
                .name("The Universe")
                .contentType("application/pdf")
                .build();

        PersonData personData = PersonData.builder()
                .birthDate(LocalDate.of(1970,1,6))
                .email("mbarek@it-2go.com")
                .firstName("Ali")
                .lastName("Mbarek")
                .gender(Gender.MALE)
                .address(Address.builder()
                        .streetOne("Rudolf-Breitscheid-Str.")
                        .buildingNr("49")
                        .city("Kaiserslautern")
                        .countryCode("DE")
                        .zipCode("67655")
                        .build())
                .build();

        Employee employee = Employee.builder()
                .publicId(UUID.randomUUID())
                .data(personData)
                .salary(2000.00)
                .traveling(true)
                .weekendWork(false)
                .documents(new ArrayList<>())
                .build();

        employee.getDocuments().add(doc1);
        employee.getDocuments().add(doc2);

        EmployeeEntity employeeEntity = employeeMapper.employeeToEmployeeEntity(employee);

        System.out.println(employeeEntity);
    }

    @Test
    void testEmployeeEntityToEmployee(){
        DocumentEntity doc1 = DocumentEntity.builder()
                .contentType("test")
                .name("file 1")
                .build();

        DocumentEntity doc2 = DocumentEntity.builder()
                .contentType("test")
                .name("file 2")
                .build();

        AddressEntity addressEntity = AddressEntity.builder()
                .buildingNr("55")
                .city("Mannheim")
                .countryCode("DE")
                .publicId(UUID.randomUUID())
                .streetOne("Bahnhofstar.")
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

        Employee employee = employeeMapper.employeeEntityToEmployee(employeeEntity);

        System.out.println(employee);
    }
}
