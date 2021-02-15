package com.it2go.micro.employeesservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Slf4j
@Configuration
@EnableJms
public class JmsConfig {

  @Bean // Serialize message content to json using TextMessage
  @Profile("jms")
  public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT); // the json payload
    converter.setTypeIdPropertyName("_type"); // the type full class name
    converter.setObjectMapper(objectMapper);

    //now set idMappings for serialization/deserialization
    //HashMap<String, Class<?>> idMapping = new HashMap<>();
    //idMapping.put(Project.class.getName(), Project.class);
    //idMapping.put(String.class.getName(), String.class);

    //converter.setTypeIdMappings(idMapping);

    return converter;
  }
}
