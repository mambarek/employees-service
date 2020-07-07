package com.it2go.micro.employeesservice.mapper;

import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
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
    EmployeeEntity simpleUpdateEmployee(@MappingTarget EmployeeEntity employeeEntity, Employee employee);
    default EmployeeEntity updateEmployeeEntity(@MappingTarget EmployeeEntity employeeEntity, Employee employee){

        // update all direct attributes
        EmployeeEntity updateEmployee = simpleUpdateEmployee(employeeEntity, employee);
        updateEmployee.getDocuments().forEach(documentEntity -> documentEntity.setOwner(updateEmployee));

        return updateEmployee;
    }

    List<DocumentEntity> updateDocumentEntityList(@MappingTarget List<DocumentEntity> documentEntities, List<Document> documents);
}
