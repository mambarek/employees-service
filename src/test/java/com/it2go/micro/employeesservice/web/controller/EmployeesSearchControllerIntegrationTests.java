package com.it2go.micro.employeesservice.web.controller;

import com.it2go.micro.employeesservice.EmployeesServiceApplication;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItem;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItemList;
import com.it2go.micro.employeesservice.search.table.EmployeesSearchTemplate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = EmployeesServiceApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeesSearchControllerIntegrationTests {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testSearch(){
    EmployeesSearchTemplate employeesSearchTemplate = new EmployeesSearchTemplate();
    Object o = restTemplate.postForObject("http://localhost:" + port + "/api/v1/search",
        employeesSearchTemplate, EmployeeTableItemList.class);
    System.out.println(o);
  }

  @Test
  public void testGetAllEmployees(){
    Object o = restTemplate.getForObject("http://localhost:" + port + "/api/v1/search",
        Employee[].class);
    System.out.println(o);
  }
}
