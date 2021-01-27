package com.it2go.micro.employeesservice.services.messagin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 25.01.2021.
 */
@Slf4j
@Component
@Profile({"!jms & !rabbit"})
public class DefaultMessagingService implements MessageService{

  @Override
  public void sendMessage(String queueName, Object body) throws SendMessageException {
    log.warn("-- Please use JMS or AMQP Profile or implement your own MessagingService!!!");
  }

  @Override
  public Object receiveMessage(String queueName) throws ReceiveMessageException {
    log.warn("-- Please use JMS or AMQP Profile or implement your own MessagingService!!!");
    return null;
  }
}
