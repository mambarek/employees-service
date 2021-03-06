package com.it2go.micro.employeesservice.bootstrap;

import com.it2go.micro.employeesservice.domian.Address;
import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.domian.PersonData;
import com.it2go.micro.employeesservice.masterdata.Gender;
import com.it2go.micro.employeesservice.services.EmployeesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmployeeLoader implements CommandLineRunner {

    private final EmployeesService employeesService;

    // test config server
    @Value("${test.config.server.message}")
    String message;

    @Override
    public void run(String... args) throws Exception {

        // test the config server
        System.out.println("-- Test the Config Server message: " + message);

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

/*    public void importProjects() throws Exception {
        jmsService.sendMessage("PROJECT_IMPORT_QUEUE", "");
        log.info("Waiting for all ActiveMQ JMS Messages to be consumed");
        TimeUnit.SECONDS.sleep(3);
        System.exit(-1);

        Object message = jmsService.receiveMessage("PROJECT_EXPORT_QUEUE");

        String projectExportEventJson = (String) message;
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

    }*/
}
