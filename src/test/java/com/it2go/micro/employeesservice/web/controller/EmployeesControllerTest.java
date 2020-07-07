package com.it2go.micro.employeesservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.employeesservice.domian.Address;
import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.domian.PersonData;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.mapper.EmployeeMapperImpl;
import com.it2go.micro.employeesservice.masterdata.Gender;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.services.EmployeesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeesController.class)
class EmployeesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EmployeeMapper employeeMapper;

    @MockBean
    EmployeesService employeesService;

    @Test
    void saveNewEmployee() throws Exception {
        Employee employee = getEmployee();
        String employeeJson = objectMapper.writeValueAsString(employee);

        // mock the service
        when(employeesService.saveNewEmployee(any())).thenReturn(employee);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(jsonPath("$.publicId").value(employee.getPublicId().toString()))
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated()).andReturn();

        String uri = mvcResult.getResponse().getHeader("Location");
        assertNotNull(uri);
        assertTrue(uri.contains(employee.getPublicId().toString()));

        System.out.println(uri);

        String jsonEmployee = mvcResult.getResponse().getContentAsString();
        assertNotNull(jsonEmployee);
        assertTrue(jsonEmployee.length() > 0);

        System.out.println(jsonEmployee);
    }

    @Test
    void updateEmployee() throws Exception {
        Employee employee = getEmployee();
        String employeeJson = objectMapper.writeValueAsString(employee);

        // mock the service
        when(employeesService.updateEmployee(any())).thenReturn(employee);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/employees/{publicId}", employee.getPublicId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(jsonPath("$.publicId").value(employee.getPublicId().toString()))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void findEmployeeByPublicId() throws Exception {

        Employee employee = getEmployee();

        // mock the service
        when(employeesService.findEmployeeByPublicId(any())).thenReturn(employee);

        MvcResult getResult = mockMvc.perform(get("/api/v1/employees/{publicId}", employee.getPublicId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.publicId").value(employee.getPublicId().toString()))
                .andExpect(status().isOk())
                .andReturn();
    }

    private Employee getEmployee(){
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

        return employee;
    }
}
