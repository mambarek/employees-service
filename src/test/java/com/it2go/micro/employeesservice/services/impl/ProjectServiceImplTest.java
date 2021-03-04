package com.it2go.micro.employeesservice.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.ProjectMapper;
import com.it2go.micro.employeesservice.mapper.ProjectMapperImpl;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.ProjectRepository;
import com.it2go.micro.employeesservice.services.EntityNotFoundException;
import com.it2go.micro.employeesservice.services.ProjectService;
import com.it2go.micro.employeesservice.services.impl.ProjectServiceImplTest.ProjectServiceConfig;
import com.it2go.micro.employeesservice.util.EmployeesProducer;
import com.it2go.micro.projectmanagement.domain.PersonData;
import com.it2go.micro.projectmanagement.domain.Project;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

/**
 * created by mmbarek on 02.03.2021.
 */
@SpringJUnitConfig(classes = {ProjectServiceConfig.class})
class ProjectServiceImplTest {

  @Configuration
  static class ProjectServiceConfig{

    @Bean
    ProjectMapper projectMapper(){
      return new ProjectMapperImpl();
    }

    @Bean
    ProjectService projectService(ProjectRepository projectRepository, ProjectMapper projectMapper, EmployeeRepository employeeRepository){
      return new ProjectServiceImpl(projectRepository, projectMapper, employeeRepository);
    }
  }

  @MockBean
  ProjectRepository projectRepository;

  @MockBean
  EmployeeRepository employeeRepository;

  @Autowired
  ProjectMapper projectMapper;

  @Autowired
  ProjectService projectService;

  ProjectEntity validProjectEntity;
  Project validProject;
  EmployeeEntity validEmployeeEntity;

  @BeforeEach
  void setUp(){
    validEmployeeEntity = new EmployeeEntity();
    validEmployeeEntity.setId(1L);
    validEmployeeEntity.setPublicId(UUID.randomUUID());
    validEmployeeEntity.setFirstName("John");

    com.it2go.micro.projectmanagement.domain.Employee employee = new com.it2go.micro.projectmanagement.domain.Employee();
    employee.setPublicId(UUID.randomUUID());
    employee.setData(new PersonData().firstName("Max").lastName("Müller"));

    validProjectEntity = new ProjectEntity();
    validProjectEntity.setId(1L);
    validProjectEntity.setPublicId(UUID.randomUUID());
    validProjectEntity.setName("Project1");
    validProjectEntity.setDescription("My first project");

    validProject = new Project();
    validProject.setPublicId(validProjectEntity.getPublicId());
    validProject.setName("Project1");
    validProject.setDescription("My first project");
    validProject.setAssignedEmployees(List.of(employee));
  }

  @Test
  void findByPublicId() {

    given(projectRepository.findByPublicId(any())).willReturn(Optional.of(validProjectEntity));

    Project byPublicId = projectService.findByPublicId(validProjectEntity.getPublicId());
    assertThat(byPublicId).isNotNull();
    assertThat(byPublicId.getPublicId()).isEqualTo(validProjectEntity.getPublicId());
  }

  @Test
  void findByPublicIdThrowsException() {

    // return no project found
    given(projectRepository.findByPublicId(any())).willReturn(Optional.empty());

    assertThatThrownBy(() -> projectService.findByPublicId(validProjectEntity.getPublicId()))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void saveProject() {
    given(projectRepository.save(any(ProjectEntity.class))).willReturn(validProjectEntity);
    given(employeeRepository.findByPublicId(any(UUID.class))).willReturn(Optional.of(validEmployeeEntity));

    Project savedProject = projectService.saveProject(validProject);

    assertThat(savedProject).isNotNull();
    assertThat(savedProject.getPublicId()).isEqualTo(validProjectEntity.getPublicId());
  }

  @Test
  void updateProject() {
    given(projectRepository.save(any(ProjectEntity.class))).willReturn(validProjectEntity);
    given(projectRepository.findByPublicId(any(UUID.class))).willReturn(Optional.of(validProjectEntity));
    given(employeeRepository.findByPublicId(any(UUID.class))).willReturn(Optional.of(validEmployeeEntity));

    Project savedProject = projectService.updateProject(validProject);

    assertThat(savedProject).isNotNull();
    assertThat(savedProject.getPublicId()).isEqualTo(validProjectEntity.getPublicId());
  }

  @Test
  void findAll() {
    given(projectRepository.findAllProjects()).willReturn(List.of(validProjectEntity));
    List<Project> all = projectService.findAll();

    assertThat(all).hasSize(1);
  }
}
