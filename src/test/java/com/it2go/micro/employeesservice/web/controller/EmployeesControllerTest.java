package com.it2go.micro.employeesservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.services.EmployeesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private Employee getEmployee(){
        Document doc1 = Document.builder()
                .name("My life")
                .contentType("application/pdf")
                .build();

        Document doc2 = Document.builder()
                .name("The Universe")
                .contentType("application/pdf")
                .build();

        Employee employee = Employee.builder()
                .publicId(UUID.randomUUID())
                .salary(2000.00)
                .traveling(true)
                .weekendWork(false)
                .documents(new ArrayList<>())
                .build();

        employee.getDocuments().add(doc1);
        employee.getDocuments().add(doc2);

        return employee;
    }

    @Test
    void saveNewEmployee() throws Exception {
        Employee employee = getEmployee();
        String employeeJson = objectMapper.writeValueAsString(employee);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isCreated()).andReturn();

        String uri = mvcResult.getResponse().getHeader("Location");
        assertNotNull(uri);
        assertTrue(uri.contains(employee.getPublicId().toString()));

        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println(contentAsString);
    }

    @Test
    void findEmployeeByPublicId() {
    }
}
