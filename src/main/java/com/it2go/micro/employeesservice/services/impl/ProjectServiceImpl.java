package com.it2go.micro.employeesservice.services.impl;

import com.it2go.micro.employeesservice.mapper.ProjectMapper;
import com.it2go.micro.employeesservice.persistence.jpa.entities.ProjectEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.ProjectRepository;
import com.it2go.micro.employeesservice.services.EntityNotFoundException;
import com.it2go.micro.employeesservice.services.ProjectService;
import com.it2go.micro.projectmanagement.domain.Project;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
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

  @Override
  public Project findByPublicId(String publicId) {
    Project project = null;
    Optional<ProjectEntity> byPublicId = projectRepository.findByPublicId(UUID.fromString(publicId));
    if(byPublicId.isPresent()){
      project = projectMapper.projectEntityToProject(byPublicId.get());
    }

    return project;
  }

  @Override
  public Project findByPublicId(UUID publicId) {
    Project project = null;
    Optional<ProjectEntity> byPublicId = projectRepository.findByPublicId(publicId);
    if(byPublicId.isPresent()){
      project = projectMapper.projectEntityToProject(byPublicId.get());
    }

    return project;
  }

  @Override
  public Project saveProject(Project project){
    ProjectEntity projectEntity = projectMapper.projectToProjectEntity(project);
    ProjectEntity savedEntity = projectRepository.save(projectEntity);

    return projectMapper.projectEntityToProject(savedEntity);
  }

  @Override
  public Project updateProject(Project project){
    ProjectEntity byPublicId = projectRepository.findByPublicId(project.getPublicId()).orElseThrow(
        EntityNotFoundException::new);

    ProjectEntity projectEntity = projectMapper.updateProject(byPublicId, project);

    ProjectEntity savedEntity = projectRepository.save(projectEntity);

    return projectMapper.projectEntityToProject(savedEntity);
  }

  @Override
  public List<Project> findAll() {
    List<Project> result = new ArrayList<>();
    Iterable<ProjectEntity> all = projectRepository.findAll();
    all.forEach(projectEntity -> {
      Project project = projectMapper.projectEntityToProject(projectEntity);
      result.add(project);
    });
    return result;
  }
}