package com.it2go.micro.employeesservice.web.controller;

import com.it2go.micro.employeesservice.services.ProjectService;
import com.it2go.micro.projectmanagement.domain.Project;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by mmbarek on 01.01.2021.
 */
@RestController
@RequiredArgsConstructor
public class ProjectsController {

  private final ProjectService projectService;

  @GetMapping("/api/v1/projects")
  public List<Project> findAllProjects(){
    return projectService.findAll();
  }
}
