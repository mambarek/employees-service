package com.it2go.micro.employeesservice.services.impl;

import com.it2go.micro.employeesservice.mapper.ProjectMapper;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.ProjectRepository;
import com.it2go.micro.employeesservice.services.EntityNotFoundException;
import com.it2go.micro.employeesservice.services.ProjectService;
import com.it2go.micro.projectmanagement.domain.Project;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * created by mmbarek on 30.12.2020.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;
  private final EmployeeRepository employeeRepository;

  @Override
  public Project findByPublicId(String publicId) {

    ProjectEntity byPublicId = projectRepository.findByPublicId(UUID.fromString(publicId)).orElseThrow(
        () -> {
          log.warn(String.format("-- Project with publicId [%s] not found", publicId));
          throw new EntityNotFoundException();
        }
    );

    return projectMapper.projectEntityToProject(byPublicId);
  }

  @Override
  public Project findByPublicId(UUID publicId) {

    return findByPublicId(publicId.toString());
  }

  @Override
  public Project saveProject(Project project){
    ProjectEntity projectEntity = projectMapper.projectToProjectEntity(project);

    List<EmployeeEntity> employeeEntities = new ArrayList<>();
    if(project.getAssignedEmployees() != null) {
      project.getAssignedEmployees().forEach(employee -> {
        EmployeeEntity byPublicId = employeeRepository
            .findByPublicId(employee.getPublicId()).orElseThrow(EntityNotFoundException::new);

        employeeEntities.add(byPublicId);
      });
    }

    projectEntity.setAssignedEmployees(employeeEntities);

    ProjectEntity savedEntity = projectRepository.save(projectEntity);

    return projectMapper.projectEntityToProject(savedEntity);
  }

  @Override
  public Project updateProject(Project project){
    ProjectEntity projectEntityByPublicId = projectRepository.findByPublicId(project.getPublicId()).orElseThrow(
        EntityNotFoundException::new);

    List<EmployeeEntity> employeeEntities = new ArrayList<>();
    if(project.getAssignedEmployees() != null) {
      project.getAssignedEmployees().forEach(employee -> {
        EmployeeEntity byPublicId = employeeRepository
            .findByPublicId(employee.getPublicId()).orElseThrow(EntityNotFoundException::new);

        employeeEntities.add(byPublicId);
      });
    }

    projectEntityByPublicId.setAssignedEmployees(employeeEntities);
    ProjectEntity projectEntity = projectMapper.updateProject(projectEntityByPublicId, project);


    ProjectEntity savedEntity = projectRepository.save(projectEntity);

    return projectMapper.projectEntityToProject(savedEntity);
  }

  @Override
  public List<Project> findAll() {
    log.info("-- findAll() Projects call");
    return projectMapper.projectEntitiesToProjects(projectRepository.findAllProjects());
  }
}
