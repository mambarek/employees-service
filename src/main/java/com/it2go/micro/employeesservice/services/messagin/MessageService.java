package com.it2go.micro.employeesservice.services.messagin;

/**
 * created by mmbarek on 24.01.2021.
 */
public interface MessageService {

  void sendMessage(String queueName, Object Object) throws SendMessageException;
  Object receiveMessage(String queueName) throws ReceiveMessageException;
}
