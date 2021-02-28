package com.it2go.micro.employeesservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.services.EmployeesService;
import com.it2go.micro.employeesservice.util.EmployeesProducer;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
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

    Employee validEmployee;
    Employee validReturnEmployee;

    @BeforeEach
    void setUp(){
        validEmployee = EmployeesProducer.createEmployee();
        validReturnEmployee = EmployeesProducer.createEmployee();
        validReturnEmployee.setCreatedAt(OffsetDateTime.now());
    }

    @AfterEach
    void tearDown() {
        reset(employeesService);
    }

    @DisplayName("Save Ops - ")
    @Nested
    public class TestSaveOperations {
        @Test
        void saveNewEmployee() throws Exception {
            String employeeJson = objectMapper.writeValueAsString(validEmployee);

            // mock the service
            when(employeesService.saveNewEmployee(any())).thenReturn(validReturnEmployee);

            MvcResult mvcResult = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(jsonPath("$.publicId").value(validEmployee.getPublicId().toString()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated()).andReturn();

            String uri = mvcResult.getResponse().getHeader("Location");
            assertNotNull(uri);
            assertTrue(uri.contains(validEmployee.getPublicId().toString()));
        }

        @Test
        void saveNewEmployeeBadRequest() throws Exception {
            validEmployee.getData().setFirstName(null);
            //validEmployee.setData(null);
            String employeeJson = objectMapper.writeValueAsString(validEmployee);

            // mock the service
            when(employeesService.saveNewEmployee(any())).thenReturn(validReturnEmployee);

            mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isBadRequest());
        }
    }

    @DisplayName("Update Ops - ")
    @Nested
    public class testUpdateOperations {
        @Test
        void updateEmployee() throws Exception {
            // employee must come from DB so use validReturnEmployee
            validReturnEmployee.setUpdatedAt(OffsetDateTime.now());
            String employeeJson = objectMapper.writeValueAsString(validReturnEmployee);

            // mock the service
            when(employeesService.updateEmployee(any())).thenReturn(validReturnEmployee);

            MvcResult mvcResult = mockMvc.perform(put("/api/v1/employees/{publicId}", validEmployee.getPublicId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(jsonPath("$.publicId").value(validEmployee.getPublicId().toString()))
                .andExpect(jsonPath("$.updatedAt").isNotEmpty())
                .andExpect(status().isOk()).andReturn();
        }

        @Test
        void updateEmployeeBadRequestWrongPublicId() throws Exception {
            String employeeJson = objectMapper.writeValueAsString(validEmployee);

            // mock the service
            when(employeesService.updateEmployee(any())).thenReturn(validReturnEmployee);

            mockMvc.perform(put("/api/v1/employees/{publicId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isBadRequest());
        }

        @Test
        void updateEmployeeBadRequestValidationFailure() throws Exception {
            validEmployee.getData().setFirstName(null);
            String employeeJson = objectMapper.writeValueAsString(validEmployee);

            // mock the service
            when(employeesService.updateEmployee(any())).thenReturn(validReturnEmployee);

            mockMvc.perform(put("/api/v1/employees/{publicId}", validEmployee.getPublicId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(employeeJson))
                .andExpect(status().isBadRequest());
        }
    }

    @Test
    void findEmployeeByPublicId() throws Exception {
        // mock the service
        when(employeesService.findEmployeeByPublicId(any())).thenReturn(validReturnEmployee);

        MvcResult getResult = mockMvc.perform(get("/api/v1/employees/{publicId}", validEmployee.getPublicId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.publicId").value(validEmployee.getPublicId().toString()))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(status().isOk())
                .andReturn();
    }

}
