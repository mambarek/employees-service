package com.it2go.micro.employeesservice.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

public class JacksonTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void givenBidirectionRelation_whenSerializing_thenException() throws JsonProcessingException {
        DocumentEntity doc1 = DocumentEntity.builder()
                .name("My Life")
                .contentType("Word")
                .build();

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .publicId(UUID.randomUUID())
                .salary(1000.0)
                .traveling(false)
                .weekendWork(false)
                .documents(new ArrayList<>())
                .build();

        employeeEntity.addDocument(doc1);
        String valueAsString = objectMapper.writeValueAsString(employeeEntity);

        System.out.println(valueAsString);
        // this assertion works only if the entities are not annotated with
        // @JsonManagedReference, @JsonBackReference
        //assertThrows(JsonMappingException.class, () -> objectMapper.writeValueAsString(employeeEntity));
        /*
        Note that:

@JsonManagedReference is the forward part of reference – the one that gets serialized normally.
@JsonBackReference is the back part of reference – it will be omitted from serialization.

Alternatively, we can also use the @JsonIgnore annotation to simply ignore one of the sides of the relationship, thus breaking the chain.

We can also use the newer @JsonView annotation to exclude one side of the relationship.
         */
    }
}
