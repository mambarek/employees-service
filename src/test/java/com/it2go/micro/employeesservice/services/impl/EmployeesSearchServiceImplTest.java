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
import com.it2go.util.jpa.search.Rule;
import com.it2go.util.jpa.search.SearchTemplate;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.EntityManager;
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
@Disabled
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

  @Test
  void filterEmployees() {
    assertThat(employeesSearchService).isNotNull();
    Employee employee = EmployeesProducer.createEmployee();
    employee.setPublicId(UUID.randomUUID());
    employee.setCreatedAt(OffsetDateTime.now());
    EmployeeEntity employeeEntity = employeeMapper.employeeToEmployeeEntity(employee);

    entityManager.persist(employeeEntity);
    entityManager.flush();

    Rule rule = new Rule();
    rule.setField(EmployeeEntity_.firstName.getName());
    rule.setData("Ali");
    Group group = new Group();
    group.getRules().add(rule);

    SearchTemplate employeesSearchTemplate = new SearchTemplate();
    employeesSearchTemplate.setFilters(group);

    System.out.println("-- employeesSearchService not null");
    System.out.println(employeesSearchService.getClass().getName());
    EmployeeTableItemList employeeTableItemList = employeesSearchService.filterEmployees(employeesSearchTemplate);

    assertThat(employeeTableItemList.getResultList()).hasSize(1);
    //System.out.println(employeeTableItemList);
  }
}
