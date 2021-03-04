package com.it2go.micro.employeesservice.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.domain.Project;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 02.03.2021.
 */
@DisplayName("Project Mapper Test - ")
class ProjectMapperTest {

  ProjectMapper projectMapper;

  @BeforeEach
  void setUp(){
    projectMapper = new ProjectMapperImpl();
  }

  @Test
  void projectEntityToProject() {

    EmployeeEntity empl1 = EmployeeEntity.builder()
        .id(1L)
        .firstName("John")
        .build();

    EmployeeEntity empl2 = EmployeeEntity.builder()
        .id(2L)
        .firstName("Max")
        .build();

    ProjectEntity projectEntity = ProjectEntity.builder()
        .id(1L)
        .publicId(UUID.randomUUID())
        .assignedEmployees(Arrays.asList(empl1, empl2))
        .name("Project1")
        .build();

    Project project = projectMapper.projectEntityToProject(projectEntity);
    assertThat(project).isNotNull();
    assertThat(project.getAssignedEmployees()).isNullOrEmpty();
  }

  @Test
  void projectEntitiesToProjects(){
    EmployeeEntity empl1 = EmployeeEntity.builder()
        .id(1L)
        .firstName("John")
        .build();

    EmployeeEntity empl2 = EmployeeEntity.builder()
        .id(2L)
        .firstName("Max")
        .build();

    ProjectEntity p1 = new ProjectEntity();
    p1.setPublicId(UUID.randomUUID());
    p1.setName("Project1");
    p1.setDescription("my project 1");
    p1.setAssignedEmployees(List.of(empl1, empl2));

    ProjectEntity p2 = new ProjectEntity();
    p2.setPublicId(UUID.randomUUID());
    p2.setName("Project2");
    p2.setDescription("my project 2");

    List<Project> projects = projectMapper.projectEntitiesToProjects(List.of(p1, p2));
    assertThat(projects).isNotNull();
    assertThat(projects).hasSize(2);

    assertThat(projects.stream()).noneMatch(project ->
        project.getAssignedEmployees() != null && project.getAssignedEmployees().size() > 0);
  }

  @Test
  void projectToProjectEntity() {
    Project p1 = new Project();
    p1.setPublicId(UUID.randomUUID());
    p1.setName("Project1");
    p1.setDescription("my project 1");

    ProjectEntity projectEntity = projectMapper.projectToProjectEntity(p1);
    assertThat(projectEntity).isNotNull();
    assertThat(projectEntity.getName()).isEqualTo(p1.getName());
  }

  @Test
  void updateProject() {
    Project project = new Project();
    project.setPublicId(UUID.randomUUID());
    project.setName("Project11");
    project.setDescription("my project 11");

    ProjectEntity projectEntity = new ProjectEntity();
    projectEntity.setPublicId(UUID.randomUUID());
    projectEntity.setName("Project1");
    projectEntity.setDescription("my project 1");

    ProjectEntity updateProject = projectMapper.updateProject(projectEntity, project);
    assertThat(updateProject).isNotNull();
    assertThat(updateProject.getName()).isEqualTo(project.getName());
    assertThat(updateProject.getDescription()).isEqualTo(project.getDescription());
  }
}
