package com.it2go.micro.employeesservice.services.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.employeesservice.config.MessagingConfig;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.events.EmployeeExportEvent;
import com.it2go.micro.employeesservice.services.EmployeesService;
import com.it2go.micro.employeesservice.services.messagin.MessageService;
import com.it2go.micro.employeesservice.services.messagin.SendMessageException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by mmbarek on 25.01.2021.
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Profile("rabbit")
public class AmqpEmployeesExporter {

  private final MessageService messageService;
  private final EmployeesService employeesService;
  private final ObjectMapper objectMapper;

  //@Transactional // why Transaction
  // JmsListener is async and running on a different thread. so it has no hibernate session in this thread
  // with @Transactional you start e new Transaction with a new session
 // @RabbitListener(queues = "EMPLOYEE_REQUEST_QUEUE")
  public void listenToEmployeeEvent(String employeePublicId) throws SendMessageException {
    log.info("-- listenToEmployeeEvent: " + employeePublicId);
    Employee employeeByPublicId = employeesService
        .findEmployeeByPublicId(UUID.fromString(employeePublicId));
    log.info("--listenToEmployeeEvent employee found " + employeeByPublicId);

    messageService.sendMessage("EMPLOYEE_RESPONSE_QUEUE", employeeByPublicId);
  }

  @Transactional // lazy load from hibernate exception
  @RabbitListener(queues = MessagingConfig.EMPLOYEES_IMPORT_QUEUE)
  public void exportAllEmployeesJson(){
    List<Employee> allEmployees = employeesService.findAllEmployees();
    EmployeeExportEvent employeeExportEvent = new EmployeeExportEvent(allEmployees);

    try {
      String valueAsString = objectMapper.writeValueAsString(employeeExportEvent);
      log.info("-- exportAllEmployees: " + valueAsString);
      //messageService.sendMessage(MessagingConfig.EMPLOYEES_EXPORT_QUEUE, valueAsString);
      messageService.sendMessage(MessagingConfig.EMPLOYEES_EXPORT_QUEUE, valueAsString);
    } catch (JsonProcessingException | SendMessageException e) {
      e.printStackTrace();
    }
  }
}
