package com.it2go.micro.employeesservice.services.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 18.01.2021.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JmsService {

  private final JmsTemplate jmsTemplate;

  public void sendMessage(String queueName, Object body) throws SendMessageException {
    try {
      jmsTemplate.convertAndSend(queueName, body);
    }
    catch (Exception e){
      System.out.println(e.getMessage());
      throw new SendMessageException("Error sending message!");
    }
  }

  public Object receiveMessage(String queueName) throws ReceiveMessageException {
    try {
      return jmsTemplate.receiveAndConvert(queueName);
    }catch (Exception e){
      System.out.println(e.getMessage());
      throw new ReceiveMessageException("Error occurred receiving message!");
    }
  }
}
