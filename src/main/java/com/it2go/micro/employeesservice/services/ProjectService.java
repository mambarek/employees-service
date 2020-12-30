package com.it2go.micro.employeesservice.services;

import com.it2go.micro.projectmanagement.domain.Project;
import java.util.List;
import java.util.UUID;

/**
 * created by mmbarek on 30.12.2020.
 */
public interface ProjectService {

  Project findByPublicId(String publicId);
  Project findByPublicId(UUID publicId);
  Project saveProject(Project project);
  Project updateProject(Project project);
  List<Project> findAll();
}
