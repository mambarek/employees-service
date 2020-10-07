package com.it2go.micro.employeesservice.services.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.projectservice.domain.Project;
import javax.jms.JMSException;
import javax.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectEventListener {

  private final JmsTemplate jmsTemplate;
  private final ObjectMapper objectMapper;

  //@JmsListener(destination = "NEW_PROJECTS_QUEUE")
  public void listenToNewProjects(String project) {
    log.info(String.format("-- listenToNewProjects() %s", project));
    //this.getProjectByPublicId(project);
  }

  //@JmsListener(destination = "PROJECTS_CHANGED_QUEUE")
  public void listenToChangedProjects(String project) {
    log.info(String.format("-- listenToChangedProjects() %s", project));
  }

  /*
    Show how use Messaging instead of Rest to get Projects
    This method describes how to get a Project with it's publicId from Project-Management-Application
    using activeMQ. jmsTemplate.sendAndReceive() sends a message and become a message back with body content
    In the other hand the listener in Project-Management-Application uses jmsTemplate.convertAndSend() to send
    a Response to the caller
   */
  //@Scheduled(fixedRate = 5000)
  private void getProjectByPublicId() {
    log.info("-- getProjectByPublicId() Scheduled");
    try {
      String pubId = "9a03a91d-8593-443f-a652-dd3a00dcfd81";
      MessageCreator mc = session -> session
          .createTextMessage(pubId);
      log.info("-- getProjectByPublicId() send message project publicId: " + pubId);
      Message message = jmsTemplate.sendAndReceive("PROJECT_REQUEST_QUEUE", mc);
      log.info("-- getProjectByPublicId() response back ");
      assert message != null;
      String projectJson = message.getBody(String.class);
      log.info("-- getProjectByPublicId() response in json: " + projectJson);
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }
}
