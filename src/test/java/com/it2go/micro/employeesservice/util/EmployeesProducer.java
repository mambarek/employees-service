package com.it2go.micro.employeesservice.util;

import com.it2go.micro.employeesservice.domian.Address;
import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.domian.PersonData;
import com.it2go.micro.employeesservice.masterdata.Gender;
import com.it2go.micro.employeesservice.persistence.jpa.entities.AddressEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class EmployeesProducer {


    public static EmployeeEntity createEmployeeEntity(){
        DocumentEntity doc1 = DocumentEntity.builder()
                .id(1L)
                .publicId(UUID.randomUUID())
                .contentType("test")
                .name("file 1")
                .build();

        DocumentEntity doc2 = DocumentEntity.builder()
                .id(2L)
                .publicId(UUID.randomUUID())
                .contentType("test")
                .name("file 2")
                .build();

        AddressEntity addressEntity = AddressEntity.builder()
                .id(1L)
                .publicId(UUID.randomUUID())
                .buildingNr("55")
                .city("Mannheim")
                .countryCode("DE")
                .publicId(UUID.randomUUID())
                .streetOne("Bahnhofstar.")
                .zipCode("12345")
                .build();

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(1L)
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

        return employeeEntity;
    }

    public static Employee createEmployee(){
        Document doc1 = Document.builder()
                .publicId(UUID.randomUUID())
                .name("My life")
                .contentType("application/pdf")
                .build();

        Document doc2 = Document.builder()
                .publicId(UUID.randomUUID())
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
                        .publicId(UUID.randomUUID())
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
                .salary(5000.00)
                .traveling(true)
                .weekendWork(false)
                .documents(new ArrayList<>())
                .build();

        employee.getDocuments().add(doc1);
        employee.getDocuments().add(doc2);

        return employee;
    }

    public static Employee createEmployee2(){
        Document doc1 = Document.builder()
                .publicId(UUID.randomUUID())
                .name("Java ist eine Insel")
                .contentType("application/pdf")
                .build();

        Document doc2 = Document.builder()
                .publicId(UUID.randomUUID())
                .name("Microservices Design")
                .contentType("application/pdf")
                .build();

        PersonData personData = PersonData.builder()
                .birthDate(LocalDate.of(1970,1,6))
                .email("guru@google.com")
                .firstName("Guru")
                .lastName("Software")
                .gender(Gender.MALE)
                .address(Address.builder()
                        .publicId(UUID.randomUUID())
                        .streetOne("Random street")
                        .buildingNr("1")
                        .city("Sidney")
                        .countryCode("DE")
                        .zipCode("67655")
                        .build())
                .build();

        Employee employee = Employee.builder()
                .publicId(UUID.randomUUID())
                .data(personData)
                .salary(3300.00)
                .traveling(true)
                .weekendWork(false)
                .documents(new ArrayList<>())
                .build();

        employee.getDocuments().add(doc1);
        employee.getDocuments().add(doc2);

        return employee;
    }
}
