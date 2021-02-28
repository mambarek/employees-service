package com.it2go.micro.employeesservice.it;

import static org.assertj.core.api.Assertions.assertThat;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity_;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItemList;
import com.it2go.micro.employeesservice.util.EmployeesProducer;
import com.it2go.util.jpa.search.Group;
import com.it2go.util.jpa.search.GroupOperation;
import com.it2go.util.jpa.search.Rule;
import com.it2go.util.jpa.search.RuleType;
import com.it2go.util.jpa.search.SearchTemplate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.*;

/**
 * created by mmbarek on 25.02.2021.
 */
@DisplayName("Employees Integration Tests -")
@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {

  public static final String API_V1_EMPLOYEES = "/api/v1/employees/";
  public static final String API_V1_EMPLOYEES_SEARCH = "/api/v1/employees/search/";

  @Autowired
  private TestRestTemplate restTemplate;

  @DisplayName("Test Employees Creation OP -")
  @Nested
  public class TestCreationOperations {

    @Test
    @Order(1)
    public void tesSave() {
      Employee employee = EmployeesProducer.createEmployee();

      Employee savedEmployee = restTemplate.postForObject(API_V1_EMPLOYEES, employee, Employee.class);

      assertThat(savedEmployee).isNotNull();
      assertThat(savedEmployee.getPublicId()).isNotNull();
      assertThat(savedEmployee.getData().getFirstName()).isEqualTo(employee.getData().getFirstName());

      employee = savedEmployee;
    }

    @Test
    @Order(2)
    public void testUpdate() {
      Employee[] employees = restTemplate.getForObject(API_V1_EMPLOYEES, Employee[].class);

      Employee employee = employees[0];
      employee.getData().setFirstName("Melcome");
      System.out.println(employee);
      System.out.println(API_V1_EMPLOYEES.concat(employee.getPublicId().toString()));

      restTemplate.put(
          API_V1_EMPLOYEES.concat(employee.getPublicId().toString()), employee);

      Employee updatedEmployee = restTemplate.getForObject(
          API_V1_EMPLOYEES.concat(employee.getPublicId().toString()), Employee.class);
      System.out.println("-- Updated Employee:");
      System.out.println(updatedEmployee);

      assertThat(updatedEmployee.getData().getFirstName()).isEqualTo(employee.getData().getFirstName());
    }
  }

  @Test
  void testGetEmployees(){
    Employee[] employees = restTemplate.getForObject(API_V1_EMPLOYEES, Employee[].class);

    assertThat(employees).isNotEmpty();
  }

  @DisplayName("Test Search Controller -")
  @Nested
  public class TestSearch {

    @Test
    public void testSearch(){
      SearchTemplate searchTemplate = new SearchTemplate();
      EmployeeTableItemList employeeTableItemList = restTemplate.postForObject(API_V1_EMPLOYEES_SEARCH, searchTemplate, EmployeeTableItemList.class);

      assertThat(employeeTableItemList).isNotNull();
      assertThat(employeeTableItemList.getResultList().size()).isEqualTo(4);
    }

    @Test
    public void testSearchWithFirstName(){
      Rule rule = new Rule();
      rule.setField(EmployeeEntity_.firstName.getName());
      rule.setData("Ali");
      Group group = new Group();
      // to be removed
      group.setGroupOp(GroupOperation.OR);
      group.getRules().add(rule);

      SearchTemplate employeesSearchTemplate = new SearchTemplate();
      employeesSearchTemplate.setFilters(group);
      EmployeeTableItemList employeeTableItemList = restTemplate.postForObject(API_V1_EMPLOYEES_SEARCH,
          employeesSearchTemplate, EmployeeTableItemList.class);

      assertThat(employeeTableItemList.getResultList()).hasSize(1);
      assertThat(employeeTableItemList.getResultList().get(0).getFirstName()).isEqualTo("Ali");
    }

    @Test
    public void testSearchWithBirthdate(){
      Rule rule = new Rule();
      rule.setField(EmployeeEntity_.birthDate.getName());
      rule.setData("1970-01-06");
      rule.setType(RuleType.DATE);
      Group group = new Group();
      // to be removed
      group.setGroupOp(GroupOperation.OR);
      group.getRules().add(rule);

      SearchTemplate employeesSearchTemplate = new SearchTemplate();
      employeesSearchTemplate.setFilters(group);
      EmployeeTableItemList employeeTableItemList = restTemplate.postForObject(API_V1_EMPLOYEES_SEARCH,
          employeesSearchTemplate, EmployeeTableItemList.class);

      assertThat(employeeTableItemList.getResultList()).hasSize(1);
    }

    @Test
    public void testSearchWithFirstNameAndBirthdate(){
      Rule firstNameRule = new Rule();
      firstNameRule.setField(EmployeeEntity_.firstName.getName());
      firstNameRule.setData("Ali");

      Rule birthDateRule = new Rule();
      birthDateRule.setField(EmployeeEntity_.birthDate.getName());
      birthDateRule.setData("1970-01-06");
      birthDateRule.setType(RuleType.DATE);

      Group group = new Group();
      group.setGroupOp(GroupOperation.AND);
      group.getRules().add(firstNameRule);
      group.getRules().add(birthDateRule);

      SearchTemplate employeesSearchTemplate = new SearchTemplate();
      employeesSearchTemplate.setFilters(group);
      EmployeeTableItemList employeeTableItemList = restTemplate.postForObject(API_V1_EMPLOYEES_SEARCH,
          employeesSearchTemplate, EmployeeTableItemList.class);

      assertThat(employeeTableItemList.getResultList()).hasSize(1);
    }

    @Test
    public void testSearchWithWrongFirstNameDoNotFindEmployee(){
      Rule rule = new Rule();
      rule.setField(EmployeeEntity_.firstName.getName());
      rule.setData("Thom");
      Group group = new Group();
      group.getRules().add(rule);

      SearchTemplate employeesSearchTemplate = new SearchTemplate();
      employeesSearchTemplate.setFilters(group);
      EmployeeTableItemList employeeTableItemList = restTemplate.postForObject(API_V1_EMPLOYEES_SEARCH,
          employeesSearchTemplate, EmployeeTableItemList.class);

      assertThat(employeeTableItemList.getResultList()).isEmpty();
    }
  }
}
