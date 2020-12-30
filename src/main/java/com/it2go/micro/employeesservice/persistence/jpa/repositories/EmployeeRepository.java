package com.it2go.micro.employeesservice.persistence.jpa.repositories;

import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByPublicId(UUID publicId);
}
