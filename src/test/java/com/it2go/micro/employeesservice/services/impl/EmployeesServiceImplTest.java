package com.it2go.micro.employeesservice.services.impl;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.mapper.EmployeeMapperImpl;
import com.it2go.micro.employeesservice.mapper.ProjectMapper;
import com.it2go.micro.employeesservice.mapper.ProjectMapperImpl;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import com.it2go.micro.employeesservice.services.EmployeesService;
import com.it2go.micro.employeesservice.services.EntityNotFoundException;
import com.it2go.micro.employeesservice.services.impl.EmployeesServiceImplTest.EmployeesServiceConfig;
import com.it2go.micro.employeesservice.util.EmployeesProducer;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * created by mmbarek on 24.02.2021.
 */
@SpringJUnitConfig(classes = {EmployeesServiceConfig.class})
class EmployeesServiceImplTest {

  @Configuration
  static class EmployeesServiceConfig {

    @Bean
    EmployeeMapper employeeMapper() {return new EmployeeMapperImpl();}

    @Bean
    ProjectMapper projectMapper(){return new ProjectMapperImpl();}

    @Bean("employeeService")
    EmployeesService employeesService(EmployeeMapper employeeMapper, EmployeeRepository employeeRepository, ProjectMapper projectMapper){
      return new EmployeesServiceImpl(employeeMapper, employeeRepository, projectMapper);}
  }

  @MockBean
  EmployeeRepository employeeRepository;

  @Autowired
  EmployeesService employeesService;

  @Autowired
  EmployeeMapper employeeMapper;

  Employee validEmployee;
  Employee validReturnEmployee;
  EmployeeEntity validEmployeeEntity;

  @BeforeEach
  void setUp(){
    validEmployee = EmployeesProducer.createEmployee();
    validReturnEmployee = EmployeesProducer.createEmployee();
    validReturnEmployee.setCreatedAt(OffsetDateTime.now());

    // Entities should have id's
    validEmployeeEntity = employeeMapper.employeeToEmployeeEntity(validReturnEmployee);
    validEmployeeEntity.setId(1L);
    validEmployeeEntity.getDocuments().forEach(documentEntity -> {
      documentEntity.setId(
          (long) (validEmployeeEntity.getDocuments().indexOf(documentEntity) + 1));
      documentEntity.setOwner(validEmployeeEntity);
    });
  }

  @AfterEach
  void tearDown() {
    reset(employeeRepository);
  }

  @DisplayName("Find Ops -")
  @Nested
  public class TestFindOperations {

    @Test
    void findAllEmployees(){
      EmployeeEntity employeeEntity = employeeMapper.employeeToEmployeeEntity(validReturnEmployee);
      given(employeeRepository.findAll()).willReturn(List.of(employeeEntity));

      List<Employee> allEmployees = employeesService.findAllEmployees();
      assertThat(allEmployees).hasSize(1);
    }

    @Test
    void findEmployeeByPublicId() {
      EmployeeEntity employeeEntity = employeeMapper.employeeToEmployeeEntity(validReturnEmployee);
      ProjectEntity p1 = new ProjectEntity();
      p1.setPublicId(UUID.randomUUID());
      p1.setName("Project1");
      p1.setDescription("my project 1");

      employeeEntity.setAssignedProjects(List.of(p1));
      // given
      given(employeeRepository.findByPublicId(any(UUID.class))).willReturn(Optional.of(employeeEntity));
      // when
      Employee employeeByPublicId = employeesService
          .findEmployeeByPublicId(employeeEntity.getPublicId());

      // then
      assertNotNull(employeeByPublicId);
      assertEquals(employeeByPublicId.getPublicId(), employeeEntity.getPublicId());
    }

    @Test
    void findEmployeeByPublicIdNotFound() {
      EmployeeEntity employeeEntity = employeeMapper.employeeToEmployeeEntity(validReturnEmployee);
      given(employeeRepository.findByPublicId(any(UUID.class))).willReturn(Optional.empty());

      assertThatThrownBy(() -> employeesService.findEmployeeByPublicId(employeeEntity.getPublicId()))
          .isInstanceOf(EntityNotFoundException.class);
    }
  }

  @Test
  void saveNewEmployee() {
    EmployeeEntity employeeEntity = employeeMapper.employeeToEmployeeEntity(validReturnEmployee);
    given(employeeRepository.save(any())).willReturn(employeeEntity);
    Employee saveNewEmployee = employeesService.saveNewEmployee(validEmployee);

    assertNotNull(saveNewEmployee);
    assertNotNull(saveNewEmployee.getPublicId());
    // the publicId would from service forgiven
    assertNotEquals(saveNewEmployee.getPublicId(), validEmployee.getPublicId());
  }

  @DisplayName("Update Ops -")
  @Nested
  public class TestUpdateOperations {

    @Test
    void updateEmployee() {
      given(employeeRepository.findByPublicId(any(UUID.class))).willReturn(Optional.of(validEmployeeEntity));
      given(employeeRepository.save(any())).willReturn(validEmployeeEntity);
      Employee updatedEmployee = employeesService.updateEmployee(validReturnEmployee);

      assertEquals(updatedEmployee.getPublicId(), validEmployeeEntity.getPublicId());
      assertNotEquals(updatedEmployee.getUpdatedAt(), validReturnEmployee.getUpdatedAt());
    }

    @Test
    void updateNotExistingEmployee() {
      given(employeeRepository.findByPublicId(any(UUID.class))).willReturn(Optional.empty());
      given(employeeRepository.save(any())).willReturn(validEmployeeEntity);

      assertThatThrownBy(() -> employeesService.updateEmployee(validReturnEmployee))
          .isInstanceOf(EntityNotFoundException.class);
    }
  }

  @Test
  void deleteEmployee(){
    given(employeeRepository.findByPublicId(any(UUID.class))).willReturn(Optional.of(validEmployeeEntity));
    doNothing().when(employeeRepository).delete(any(EmployeeEntity.class));

    employeesService.deleteEmployee(UUID.randomUUID());
  }

  @Test
  void deleteEmployeeShouldThrowException(){
    given(employeeRepository.findByPublicId(any(UUID.class))).willReturn(Optional.empty());
    doNothing().when(employeeRepository).delete(any(EmployeeEntity.class));

    assertThatThrownBy(() -> employeesService.deleteEmployee(UUID.randomUUID()))
        .isInstanceOf(EntityNotFoundException.class);

  }
}
