package com.it2go.micro.employeesservice.services.impl;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.mapper.EmployeeMapper;
import com.it2go.micro.employeesservice.mapper.ProjectMapper;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.repositories.EmployeeRepository;
import com.it2go.micro.employeesservice.services.EmployeesService;
import com.it2go.micro.employeesservice.services.EntityNotFoundException;
import com.it2go.micro.projectmanagement.domain.Project;
import java.util.ArrayList;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmployeesService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<Employee> findAllEmployees() {

        log.info("--Employees#findAllEmployees");

        List<Employee> result = new ArrayList<>();

        employeeRepository.findAll().forEach(
            employeeEntity -> result.add(employeeMapper.employeeEntityToEmployee(employeeEntity))
        );
        log.info(String.format("--Employees#findAllEmployees found %s", result.size()));
        return result;
    }

    @Override
    public Employee findEmployeeByPublicId(UUID publicId) throws EntityNotFoundException{

        log.info(String.format("--findEmployeeByPublicId [%s]", publicId));

        EmployeeEntity employeeEntity = employeeRepository.findByPublicId(publicId).orElseThrow(
            () -> {
                log.warn(String.format("-- Employee with publicId [%s] not found", publicId));
                throw new EntityNotFoundException();
            }
        );

        List<Project> projects = new ArrayList<>();
        if(employeeEntity.getAssignedProjects() != null) {
            employeeEntity.getAssignedProjects().forEach(projectEntity -> {
                Project project = projectMapper.projectEntityToProject(projectEntity);
                projects.add(project);
            });
        }

        Employee employee = employeeMapper.employeeEntityToEmployee(employeeEntity);
        employee.setAssignedProjects(projects);

        return employee;
    }

    @Override
    public Employee saveNewEmployee(Employee employee) {
        log.info("-- saveNewEmployee");
        Objects.requireNonNull(employee, "Employee should not be Null!");
        Objects.requireNonNull(employee.getData(), "Employee personal data should not be Null");
        // publicId may be requested from another service e.g. PublicIdService.getNewPublicId(entityClass)
        employee.setPublicId(UUID.randomUUID());
        employee.setCreatedAt(OffsetDateTime.now());
        employee.setCreatedBy(UUID.randomUUID()); // TODO to be changed with user publicId

        if(employee.getData().getAddress() != null){
            employee.getData().getAddress().setPublicId(UUID.randomUUID());
        }

        log.info(String.format("-- saveNewEmployee publicId: [%s]", employee.getPublicId()));
        EmployeeEntity employeeEntity = employeeRepository.save(employeeMapper.employeeToEmployeeEntity(employee));
        log.info(String.format("-- saveNewEmployee [%s] successfully created", employee.getPublicId()));

        return employeeMapper.employeeEntityToEmployee(employeeEntity);
    }

    @Override
    public Employee updateEmployee(Employee employee){
        log.info("-- updateEmployee");
        Objects.requireNonNull(employee, "Employee should not be Null!");

        log.info(String.format("-- updateEmployee update [%s]", employee.getPublicId()));
        EmployeeEntity byPublicId = employeeRepository.findByPublicId(employee.getPublicId())
                .orElseThrow(
                    () -> {
                        log.warn(String.format("-- deleteEmployee: Employee with publicId [%s] not found",
                            employee.getPublicId()));
                        throw new EntityNotFoundException();
                    }
                );

        EmployeeEntity employeeEntity = employeeMapper.updateEmployeeEntity(byPublicId, employee);
        employeeEntity.setUpdatedAt(OffsetDateTime.now());
        employeeEntity.setUpdatedBy(UUID.randomUUID()); // TODO to be changed with user publicId

        EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);
        log.info(String.format("-- updateNewEmployee [%s] updated successfully", employee.getPublicId()));

        return employeeMapper.employeeEntityToEmployee(savedEntity);
    }

    @Override
    public void deleteEmployee(UUID publicId) {
        log.info("-- deleteEmployee");
        Objects.requireNonNull(publicId, "PublicId should not be Null!");

        EmployeeEntity byPublicId = employeeRepository.findByPublicId(publicId).orElseThrow(
            () -> {
                log.warn(String.format("-- deleteEmployee: Employee with publicId [%s] not found", publicId));
                throw new EntityNotFoundException();
            }
        );

        employeeRepository.delete(byPublicId);
        log.info(String.format("-- deleteEmployee [%s] deleted successfully", publicId));
    }

    @Override
    public Long countEmployees() {
        log.info("-- countEmployees");
        return employeeRepository.count();
    }
}
