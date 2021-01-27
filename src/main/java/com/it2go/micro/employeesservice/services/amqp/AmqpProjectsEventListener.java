package com.it2go.micro.employeesservice.services.amqp;

import com.it2go.micro.employeesservice.config.MessagingConfig;
import com.it2go.micro.employeesservice.services.ProjectService;
import com.it2go.micro.projectmanagement.domain.Project;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 25.01.2021.
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("rabbit")
public class AmqpProjectsEventListener {

  private final ProjectService projectService;

  @RabbitListener(queues = MessagingConfig.NEW_PROJECTS_QUEUE)
  public void listenToNewProjects(Project project) {
    log.info(String.format("-- listenToNewProjects() %s", project));
    System.out.println(String.format("-- listenToNewProjects() %s", project));
    projectService.saveProject(project);
  }

  @RabbitListener(queues = MessagingConfig.UPDATED_PROJECTS_QUEUE)
  public void listenToChangedProjects(Project project) {
    log.info(String.format("-- listenToChangedProjects() %s", project));
    projectService.updateProject(project);
  }
}
