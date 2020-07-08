package com.it2go.micro.employeesservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.services.EmployeesService;
import com.it2go.micro.employeesservice.util.EmployeesProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        Employee employee = EmployeesProducer.createEmployee();
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
        Employee employee = EmployeesProducer.createEmployee();
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

        Employee employee = EmployeesProducer.createEmployee();

        // mock the service
        when(employeesService.findEmployeeByPublicId(any())).thenReturn(employee);

        MvcResult getResult = mockMvc.perform(get("/api/v1/employees/{publicId}", employee.getPublicId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.publicId").value(employee.getPublicId().toString()))
                .andExpect(status().isOk())
                .andReturn();
    }

}
