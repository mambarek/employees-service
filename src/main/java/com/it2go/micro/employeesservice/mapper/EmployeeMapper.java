package com.it2go.micro.employeesservice.mapper;

import com.it2go.micro.employeesservice.domian.Employee;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmployeeMapper{

    @Mapping(source = "firstName", target = "data.firstName")
    @Mapping(source = "lastName", target = "data.lastName")
    @Mapping(source = "birthDate", target = "data.birthDate")
    @Mapping(source = "gender", target = "data.gender")
    @Mapping(source = "email", target = "data.email")
    @Mapping(source = "address", target = "data.address")
    Employee employeeEntityToEmployee(EmployeeEntity employeeEntity);

    @InheritInverseConfiguration
    EmployeeEntity simpleEmployeeToEmployeeEntity(Employee employee);

    default EmployeeEntity employeeToEmployeeEntity(Employee employee){
        EmployeeEntity employeeEntity = this.simpleEmployeeToEmployeeEntity(employee);
        employeeEntity.getDocuments().forEach(documentEntity -> documentEntity.setOwner(employeeEntity));

        return employeeEntity;
    };

}
