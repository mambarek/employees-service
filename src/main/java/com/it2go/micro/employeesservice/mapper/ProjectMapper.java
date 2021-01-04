package com.it2go.micro.employeesservice.mapper;

import com.it2go.micro.employeesservice.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.domain.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * created by mmbarek on 28.12.2020.
 */
@Mapper
public interface ProjectMapper {

  @Mapping(source = "assignedEmployees", target = "assignedEmployees", ignore = true)
  Project projectEntityToProject(ProjectEntity projectEntity);

  @Mapping(source = "assignedEmployees", target = "assignedEmployees", ignore = true)
  ProjectEntity projectToProjectEntity(Project project);

  @Mapping(source = "assignedEmployees", target = "assignedEmployees", ignore = true)
  ProjectEntity updateProject(@MappingTarget ProjectEntity projectEntity, Project project);

}
