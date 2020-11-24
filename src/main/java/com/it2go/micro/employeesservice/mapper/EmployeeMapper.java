package com.it2go.micro.employeesservice.mapper;

import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface EmployeeMapper{

    @Mapping(source = "firstName", target = "data.firstName")
    @Mapping(source = "lastName", target = "data.lastName")
    @Mapping(source = "birthDate", target = "data.birthDate")
    @Mapping(source = "gender", target = "data.gender")
    @Mapping(source = "email", target = "data.email")
    @Mapping(source = "address", target = "data.address")
    Employee employeeEntityToEmployee(EmployeeEntity employeeEntity);

    @InheritInverseConfiguration(name = "employeeEntityToEmployee")
    EmployeeEntity simpleEmployeeToEmployeeEntity(Employee employee);

    default EmployeeEntity employeeToEmployeeEntity(Employee employee){
        EmployeeEntity employeeEntity = this.simpleEmployeeToEmployeeEntity(employee);
        employeeEntity.getDocuments().forEach(documentEntity -> documentEntity.setOwner(employeeEntity));

        return employeeEntity;
    };

    @Mapping(target = "firstName", source = "data.firstName")
    @Mapping(target = "lastName", source = "data.lastName")
    @Mapping(target = "birthDate", source = "data.birthDate")
    @Mapping(target = "gender", source = "data.gender")
    @Mapping(target = "email", source = "data.email")
    @Mapping(target = "address", source = "data.address")
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    EmployeeEntity simpleUpdateEmployee(@MappingTarget EmployeeEntity employeeEntity, Employee employee);

    default EmployeeEntity updateEmployeeEntity(@MappingTarget EmployeeEntity employeeEntity, Employee employee){

        // gather all ids for publicIds
        Map<UUID, Long> idsMap = employeeEntity.getDocuments().stream()
            .collect(Collectors.toMap(DocumentEntity::getPublicId, DocumentEntity::getId));

        // update all direct attributes
        EmployeeEntity updateEmployee = simpleUpdateEmployee(employeeEntity, employee);
        // and now the reference
        updateEmployee.getDocuments().forEach(documentEntity -> {
            documentEntity.setId(idsMap.get(documentEntity.getPublicId()));
            documentEntity.setOwner(updateEmployee);
        });

        return updateEmployee;
    }

    // implement explicitly an update for the list this would be
    // checked and called automatically from MapStruct in simpleUpdateEmployee
    List<DocumentEntity> updateDocumentEntityList(@MappingTarget List<DocumentEntity> documentEntities, List<Document> documents);
}
