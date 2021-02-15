package com.it2go.micro.employeesservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * created by mmbarek on 24.01.2021.
 */
@Configuration
@Profile("rabbit")
public class AmqpConfig {

  @Bean
  Queue newProjectsQueue(){
    return new Queue(MessagingConfig.NEW_PROJECTS_QUEUE);
  }

  @Bean
  Queue updatedProjectsQueue(){
    return new Queue(MessagingConfig.UPDATED_PROJECTS_QUEUE);
  }

  @Bean
  Queue employeesImportQueue(){
    return new Queue(MessagingConfig.EMPLOYEES_IMPORT_QUEUE);
  }

  @Bean
  Queue employeesExportQueue(){
    return new Queue(MessagingConfig.EMPLOYEES_EXPORT_QUEUE);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
    final var rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter(objectMapper));
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter(ObjectMapper objectMapper) {
    Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
    return converter;
  }
}
