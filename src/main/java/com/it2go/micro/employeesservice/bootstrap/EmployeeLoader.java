package com.it2go.micro.employeesservice.bootstrap;

import com.it2go.micro.employeesservice.persistence.jpa.entities.AddressEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EmployeeLoader implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        DocumentEntity doc1 = DocumentEntity.builder()
                .publicId(UUID.randomUUID())
                .contentType("test")
                .name("file 1")
                .build();

        DocumentEntity doc2 = DocumentEntity.builder()
                .publicId(UUID.randomUUID())
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

        employeeRepository.save(employeeEntity);
        System.out.println(employeeEntity);
    }
}
