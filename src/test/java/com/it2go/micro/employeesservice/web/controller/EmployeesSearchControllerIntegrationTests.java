package com.it2go.micro.employeesservice.web.controller;

import com.it2go.micro.employeesservice.EmployeesServiceApplication;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity_;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItemList;
import com.it2go.micro.employeesservice.search.table.EmployeesSearchTemplate;
import de.it2go.util.jpa.search.Group;
import de.it2go.util.jpa.search.Rule;
import de.it2go.util.jpa.search.RuleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

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
  public void testSearchWithRuleAndFindEmployee(){
    Rule rule = new Rule();
    rule.setField(EmployeeEntity_.firstName.getName());
    rule.setData("Ali");
    Group group = new Group();
    group.getRules().add(rule);

    EmployeesSearchTemplate employeesSearchTemplate = new EmployeesSearchTemplate();
    employeesSearchTemplate.setFilters(group);
    Object o = restTemplate.postForObject("http://localhost:" + port + "/api/v1/search",
        employeesSearchTemplate, EmployeeTableItemList.class);
    System.out.println(o);
  }

  @Test
  public void testSearchWithBirthdayRuleAndFindEmployee(){
    Rule rule = new Rule();
    rule.setField(EmployeeEntity_.birthDate.getName());
    rule.setData("1970-01-06");
    rule.setType(RuleType.DATE);
    Group group = new Group();
    group.getRules().add(rule);

    EmployeesSearchTemplate employeesSearchTemplate = new EmployeesSearchTemplate();
    employeesSearchTemplate.setFilters(group);
    Object o = restTemplate.postForObject("http://localhost:" + port + "/api/v1/search",
        employeesSearchTemplate, EmployeeTableItemList.class);
    System.out.println(o);
  }

  @Test
  public void testSearchWithRuleDoNotFindEmployee(){
    Rule rule = new Rule();
    rule.setField(EmployeeEntity_.firstName.getName());
    rule.setData("Thom");
    Group group = new Group();
    group.getRules().add(rule);

    EmployeesSearchTemplate employeesSearchTemplate = new EmployeesSearchTemplate();
    employeesSearchTemplate.setFilters(group);
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
