package com.it2go.micro.employeesservice.services.jms;

import com.it2go.micro.employeesservice.services.messagin.MessageService;
import com.it2go.micro.employeesservice.services.messagin.ReceiveMessageException;
import com.it2go.micro.employeesservice.services.messagin.SendMessageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 18.01.2021.
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Profile("jms")
public class JmsService implements MessageService {

  private final JmsTemplate jmsTemplate;

  public void sendMessage(String queueName, Object body) throws SendMessageException {
    log.info("-- jms send message!");
    try {
      jmsTemplate.convertAndSend(queueName, body);
    }
    catch (Exception e){
      System.out.println(e.getMessage());
      throw new SendMessageException("Error sending message!");
    }
  }

  public Object receiveMessage(String queueName) throws ReceiveMessageException {
    log.info("-- jms receive message!");
    try {
      return jmsTemplate.receiveAndConvert(queueName);
    }catch (Exception e){
      System.out.println(e.getMessage());
      throw new ReceiveMessageException("Error occurred receiving message!");
    }
  }
}
