package com.it2go.micro.employeesservice.mapper;

import com.it2go.micro.employeesservice.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.projectmanagement.domain.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * created by mmbarek on 28.12.2020.
 */
@Mapper
public interface ProjectMapper {

  Project projectEntityToProject(ProjectEntity projectEntity);

  ProjectEntity projectToProjectEntity(Project project);
  ProjectEntity updateProject(@MappingTarget ProjectEntity projectEntity, Project project);

}
