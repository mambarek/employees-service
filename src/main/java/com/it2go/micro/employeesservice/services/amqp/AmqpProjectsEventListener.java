package com.it2go.micro.employeesservice.services.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.employeesservice.config.MessagingConfig;
import com.it2go.micro.employeesservice.services.ProjectService;
import com.it2go.micro.projectmanagement.domain.Project;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
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
  private final ObjectMapper objectMapper;

  @RabbitListener(queues = MessagingConfig.NEW_PROJECTS_QUEUE)
  public void listenToNewProjects(final Project project) {
    log.info(String.format("-- listenToNewProjects() %s", project));
    System.out.println(String.format("-- listenToNewProjects() %s", project));
    projectService.saveProject(project);
  }

  /**
   * this approach makes the microservices dependent because the parsed object must be
   * the same class and package as in the sender service. This makes the services dependent and
   * the sender service should be in java two not in TypeScript or Python
   */
/*  @RabbitListener(queues = MessagingConfig.UPDATED_PROJECTS_QUEUE)
  public void listenToChangedProjects(final Project project) {
    log.info(String.format("-- listenToChangedProjects() %s", project));
    projectService.updateProject(project);
  }*/

  /**
   * We can use a generic Message to avoid dependencies between microservices
   * First read the message body as json text, then map it to domain object
   * @param message
   * @throws JsonProcessingException
   */
  @RabbitListener(queues = MessagingConfig.UPDATED_PROJECTS_QUEUE)
  public void listenToChangeProjectsGeneric(final Message message) throws JsonProcessingException {
    String bodyJson= new String(message.getBody(), StandardCharsets.UTF_8);
    log.info(String.format("-- listenToChangeProjectsGeneric() body json %s", bodyJson));
    Project project = objectMapper.readValue(bodyJson, Project.class);
    projectService.updateProject(project);
  }
}
