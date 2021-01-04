package com.it2go.micro.employeesservice.bootstrap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.employeesservice.domian.Address;
import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.domian.PersonData;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.masterdata.Gender;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import com.it2go.micro.employeesservice.services.EmployeesService;
import com.it2go.micro.employeesservice.services.ProjectService;
import com.it2go.micro.projectmanagement.domain.ProjectExportEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EmployeeLoader implements CommandLineRunner {

    private final JmsTemplate jmsTemplate;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ProjectService projectService;
    private final ObjectMapper objectMapper;

    private final EmployeesService employeesService;

    @Override
    public void run(String... args) throws Exception {
        Employee employee = employeesService.saveNewEmployee(createEmployee());
        employeesService.saveNewEmployee(createEmployee2());
        employeesService.saveNewEmployee(createEmployee3());
        employeesService.saveNewEmployee(createEmployee4());
        // disable import
        //importProjects();
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
            .name("Book 2")
            .contentType("application/pdf")
            .build();

        Document doc2 = Document.builder()
            .publicId(UUID.randomUUID())
            .name("The Document")
            .contentType("application/pdf")
            .build();

        PersonData personData = PersonData.builder()
            .birthDate(LocalDate.of(1985,6,23))
            .email("john.doe@gmail.com")
            .firstName("John")
            .lastName("Doe")
            .gender(Gender.MALE)
            .address(Address.builder()
                .publicId(UUID.randomUUID())
                .streetOne("Wall street")
                .buildingNr("350")
                .city("London")
                .countryCode("GB")
                .zipCode("77777")
                .build())
            .build();

        Employee employee = Employee.builder()
            .publicId(UUID.randomUUID())
            .data(personData)
            .salary(3500.00)
            .traveling(true)
            .weekendWork(true)
            .documents(new ArrayList<>())
            .build();

        employee.getDocuments().add(doc1);
        employee.getDocuments().add(doc2);

        return employee;
    }

    public static Employee createEmployee3(){
        Document doc1 = Document.builder()
            .publicId(UUID.randomUUID())
            .name("My dream")
            .contentType("application/pdf")
            .build();

        Document doc2 = Document.builder()
            .publicId(UUID.randomUUID())
            .name("Godzilla")
            .contentType("application/pdf")
            .build();

        PersonData personData = PersonData.builder()
            .birthDate(LocalDate.of(1995,9,3))
            .email("Alice.Brown@gmail.com")
            .firstName("Alice")
            .lastName("Brown")
            .gender(Gender.FEMALE)
            .address(Address.builder()
                .publicId(UUID.randomUUID())
                .streetOne("21 street")
                .buildingNr("50")
                .city("Boston")
                .countryCode("US")
                .zipCode("ab-234")
                .build())
            .build();

        Employee employee = Employee.builder()
            .publicId(UUID.randomUUID())
            .data(personData)
            .salary(4500.00)
            .traveling(false)
            .weekendWork(true)
            .documents(new ArrayList<>())
            .build();

        employee.getDocuments().add(doc1);
        employee.getDocuments().add(doc2);

        return employee;
    }

    public static Employee createEmployee4(){
        Document doc1 = Document.builder()
            .publicId(UUID.randomUUID())
            .name("Fly")
            .contentType("application/pdf")
            .build();

        Document doc2 = Document.builder()
            .publicId(UUID.randomUUID())
            .name("Tibet")
            .contentType("application/pdf")
            .build();

        PersonData personData = PersonData.builder()
            .birthDate(LocalDate.of(1995,9,3))
            .email("Angela.Thomson@gmail.com")
            .firstName("Angela")
            .lastName("Thomson")
            .gender(Gender.FEMALE)
            .address(Address.builder()
                .publicId(UUID.randomUUID())
                .streetOne("67 street")
                .buildingNr("750")
                .city("Newyork")
                .countryCode("US")
                .zipCode("a2-555")
                .build())
            .build();

        Employee employee = Employee.builder()
            .publicId(UUID.randomUUID())
            .data(personData)
            .salary(5500.00)
            .traveling(false)
            .weekendWork(false)
            .documents(new ArrayList<>())
            .build();

        employee.getDocuments().add(doc1);
        employee.getDocuments().add(doc2);

        return employee;
    }

    public void importProjects(){
        jmsTemplate.convertAndSend("PROJECT_IMPORT_QUEUE", "");
        String projectExportEventJson = (String) jmsTemplate.receiveAndConvert("PROJECT_EXPORT_QUEUE");
        System.out.println("-- All Projects");
        System.out.println(projectExportEventJson);
        if(projectExportEventJson == null) return;

        System.out.println("-- Project import save all projects!");
        try {
            ProjectExportEvent projectExportEvent = objectMapper
                .readValue(projectExportEventJson, ProjectExportEvent.class);
            projectExportEvent.getProjects().forEach(projectService::saveProject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
