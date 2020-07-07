package com.it2go.micro.employeesservice.bootstrap;

import com.it2go.micro.employeesservice.domian.Address;
import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.domian.PersonData;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.masterdata.Gender;
import com.it2go.micro.employeesservice.persistence.jpa.entities.AddressEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EmployeeLoader implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public void run(String... args) throws Exception {
        EmployeeEntity employeeEntity = employeeMapper.employeeToEmployeeEntity(createEmployee());

        employeeRepository.save(employeeEntity);
        System.out.println(employeeEntity);
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
}
