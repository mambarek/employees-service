package com.it2go.micro.employeesservice.mapper;

import com.it2go.micro.employeesservice.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.domain.Project;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * created by mmbarek on 28.12.2020.
 */
@Mapper
public interface ProjectMapper {

  /**
   * Project is generated from OpenAPI Definitions with attribute assignedEmployees
   * this attribute is here not needed in the logic or UI because project is manyToOne reference
   * so ignore it
   */

  @Mapping(source = "assignedEmployees", target = "assignedEmployees", ignore = true)
  Project projectEntityToProject(ProjectEntity projectEntity);

  List<Project> projectEntitiesToProjects(List<ProjectEntity> projectEntities);

  @Mapping(source = "assignedEmployees", target = "assignedEmployees", ignore = true)
  ProjectEntity projectToProjectEntity(Project project);

  @Mapping(source = "assignedEmployees", target = "assignedEmployees", ignore = true)
  ProjectEntity updateProject(@MappingTarget ProjectEntity projectEntity, Project project);
}
