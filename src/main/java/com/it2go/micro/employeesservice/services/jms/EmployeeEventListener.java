package com.it2go.micro.employeesservice.services.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.events.EmployeeExportEvent;
import com.it2go.micro.employeesservice.services.EmployeesService;
import java.util.List;
import java.util.UUID;
import javax.jms.JMSException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by mmbarek on 01.01.2021.
 */
@Slf4j
@RequiredArgsConstructor
@Component
@Profile("jms")
public class EmployeeEventListener {

  private final JmsTemplate jmsTemplate;
  private final EmployeesService employeesService;
  private final ObjectMapper objectMapper;

  @Transactional // why Transaction
  // JmsListener is async and running on a different thread. so it has no hibernate session in this thread
  // with @Transactional you start e new Transaction with a new session
  @JmsListener(destination = "EMPLOYEE_REQUEST_QUEUE")
  public void listenToEmployeeEvent(String employeePublicId) throws JMSException {
    log.info("-- listenToEmployeeEvent: " + employeePublicId);
    Employee employeeByPublicId = employeesService
        .findEmployeeByPublicId(UUID.fromString(employeePublicId));
    log.info("--listenToEmployeeEvent employee found " + employeeByPublicId);

    jmsTemplate.convertAndSend("EMPLOYEE_RESPONSE_QUEUE", employeeByPublicId);
  }

  @Transactional // lazy load from hibernate exception
  @JmsListener(destination = "EMPLOYEE_IMPORT_QUEUE")
  public void exportAllEmployeesJson(){
    List<Employee> allEmployees = employeesService.findAllEmployees();
    EmployeeExportEvent employeeExportEvent = new EmployeeExportEvent(allEmployees);

    try {
      String valueAsString = objectMapper.writeValueAsString(employeeExportEvent);
      jmsTemplate.convertAndSend("EMPLOYEE_EXPORT_QUEUE", valueAsString);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
