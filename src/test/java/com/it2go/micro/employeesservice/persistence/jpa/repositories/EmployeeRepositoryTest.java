package com.it2go.micro.employeesservice.persistence.jpa.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.mapper.EmployeeMapperImpl;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.util.EmployeesProducer;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.*;

/**
 * created by mmbarek on 27.02.2021.
 */
@DataJpaTest
class EmployeeRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private EmployeeRepository employeeRepository;

  private final EmployeeMapper employeeMapper = new EmployeeMapperImpl();

  @Test
  void findByPublicId() {
    Employee employee = EmployeesProducer.createEmployee();
    employee.setPublicId(UUID.randomUUID());
    employee.setCreatedAt(OffsetDateTime.now());
    EmployeeEntity employeeEntity = employeeMapper.employeeToEmployeeEntity(employee);

    entityManager.persist(employeeEntity);
    entityManager.flush();

    Optional<EmployeeEntity> byPublicIdOptional = employeeRepository.findByPublicId(employee.getPublicId());
    assertThat(byPublicIdOptional.isEmpty()).isFalse();
  }
}
