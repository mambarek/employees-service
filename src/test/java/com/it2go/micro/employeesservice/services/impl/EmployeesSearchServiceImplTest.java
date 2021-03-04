package com.it2go.micro.employeesservice.services.impl;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.mapper.EmployeeMapperImpl;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity_;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItemList;
import com.it2go.micro.employeesservice.services.EmployeesSearchService;
import com.it2go.micro.employeesservice.services.impl.EmployeesSearchServiceImplTest.EmployeesSearchServiceConfiguration;
import com.it2go.micro.employeesservice.util.EmployeesProducer;
import com.it2go.util.jpa.search.Group;
import com.it2go.util.jpa.search.Operation;
import com.it2go.util.jpa.search.Rule;
import com.it2go.util.jpa.search.SearchOrder;
import com.it2go.util.jpa.search.SearchTemplate;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.*;

/**
 * created by mmbarek on 24.02.2021.
 */
@DataJpaTest
//@SpringJUnitConfig(classes = {EmployeesSearchServiceConfiguration.class})
class EmployeesSearchServiceImplTest {

  @Autowired
  private TestEntityManager entityManager;

  @TestConfiguration
  public static class EmployeesSearchServiceConfiguration {

    @Autowired
    private EntityManager entityManager;

    @Bean
    public EmployeesSearchService employeesSearchService(){
      return new EmployeesSearchServiceImpl(entityManager);
    }
  }

  @Autowired
  EmployeesSearchService employeesSearchService;

  private final EmployeeMapper employeeMapper = new EmployeeMapperImpl();

  @BeforeEach
  void setup(){
    Employee empl1 = EmployeesProducer.createEmployee();
    EmployeeEntity employeeEntity1 = employeeMapper.employeeToEmployeeEntity(empl1);

    Employee empl2 = EmployeesProducer.createEmployee2();
    EmployeeEntity employeeEntity2 = employeeMapper.employeeToEmployeeEntity(empl2);

    entityManager.persist(employeeEntity1);
    entityManager.persist(employeeEntity2);
    entityManager.flush();
  }

  @Test
  void filterEmployees() {
    assertThat(employeesSearchService).isNotNull();

    Rule rule = new Rule();
    rule.setField(EmployeeEntity_.firstName.getName());
    // search is case sensitive
    rule.setData("A");
    rule.setOp(Operation.CONTAINS);
    Group group = new Group();
    group.getRules().add(rule);

    SearchTemplate employeesSearchTemplate = new SearchTemplate();
    employeesSearchTemplate.setFilters(group);

    EmployeeTableItemList employeeTableItemList = employeesSearchService.filterEmployees(employeesSearchTemplate);

    assertThat(employeeTableItemList.getResultList()).hasSize(1);
    //System.out.println(employeeTableItemList);
  }

  @Test
  void filterEmployeesMax() {
    assertThat(employeesSearchService).isNotNull();

    Rule rule = new Rule();
    rule.setField(EmployeeEntity_.firstName.getName());
    rule.setData("A");
    rule.setOp(Operation.CONTAINS);
    Group group = new Group();
    group.getRules().add(rule);

    SearchTemplate employeesSearchTemplate = new SearchTemplate();
    employeesSearchTemplate.setMaxResult(1);
    employeesSearchTemplate.setFilters(group);

    EmployeeTableItemList employeeTableItemList = employeesSearchService.filterEmployees(employeesSearchTemplate);

    assertThat(employeeTableItemList.getResultList()).hasSize(1);
    System.out.println(employeeTableItemList);
  }

  @Test
  void filterEmployeesSortDes() {
    assertThat(employeesSearchService).isNotNull();

    Rule rule = new Rule();
    rule.setField(EmployeeEntity_.firstName.getName());
    rule.setData("i");
    rule.setOp(Operation.CONTAINS);
    Group group = new Group();
    group.getRules().add(rule);

    SearchTemplate employeesSearchTemplate = new SearchTemplate();
    // orderby attribute and search order are both needed
    employeesSearchTemplate.setOrderBy(EmployeeEntity_.firstName.getName());
    employeesSearchTemplate.setOrderDirection(SearchOrder.DESC);
    employeesSearchTemplate.setFilters(group);

    EmployeeTableItemList employeeTableItemList = employeesSearchService.filterEmployees(employeesSearchTemplate);

    assertThat(employeeTableItemList.getResultList()).hasSize(2);
    assertThat(employeeTableItemList.getResultList().get(0).getFirstName()).isEqualTo("Catrin");
    System.out.println(employeeTableItemList);
  }

  @Test
  void filterEmployeesSortAsc() {
    assertThat(employeesSearchService).isNotNull();

    Rule rule = new Rule();
    rule.setField(EmployeeEntity_.firstName.getName());
    rule.setData("i");
    rule.setOp(Operation.CONTAINS);
    Group group = new Group();
    group.getRules().add(rule);

    SearchTemplate employeesSearchTemplate = new SearchTemplate();
    // orderby attribute and search order are both needed
    employeesSearchTemplate.setOrderBy(EmployeeEntity_.firstName.getName());
    employeesSearchTemplate.setOrderDirection(SearchOrder.ASC);
    employeesSearchTemplate.setFilters(group);

    EmployeeTableItemList employeeTableItemList = employeesSearchService.filterEmployees(employeesSearchTemplate);

    assertThat(employeeTableItemList.getResultList()).hasSize(2);
    assertThat(employeeTableItemList.getResultList().get(0).getFirstName()).isEqualTo("Ali");
    System.out.println(employeeTableItemList);
  }
}
