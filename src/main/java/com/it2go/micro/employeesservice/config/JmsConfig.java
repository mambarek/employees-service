package com.it2go.micro.employeesservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class JmsConfig {

  //@Bean // Serialize message content to json using TextMessage
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT); // the json payload
    //converter.setTypeIdPropertyName("_type"); // the type full class name
    converter.setTypeIdPropertyName("com.it2go.micro.projectservice.domain.Project");

    return converter;
  }
}
