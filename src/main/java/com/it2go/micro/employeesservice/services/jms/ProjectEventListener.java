package com.it2go.micro.employeesservice.services.jms;

import com.it2go.micro.employeesservice.services.ProjectService;
import com.it2go.micro.projectmanagement.domain.Project;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("jms")
public class ProjectEventListener {

  private final ProjectService projectService;

  @JmsListener(destination = "NEW_PROJECTS_QUEUE")
  public void listenToNewProjects(Project project) {
    log.info(String.format("-- listenToNewProjects() %s", project));
    System.out.println(String.format("-- listenToNewProjects() %s", project));
    projectService.saveProject(project);
  }

  @JmsListener(destination = "PROJECTS_CHANGED_QUEUE")
  public void listenToChangedProjects(Project project) {
    log.info(String.format("-- listenToChangedProjects() %s", project));
    projectService.updateProject(project);
  }

}
