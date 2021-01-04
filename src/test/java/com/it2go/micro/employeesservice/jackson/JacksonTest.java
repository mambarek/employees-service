package com.it2go.micro.employeesservice.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.projectmanagement.domain.ProjectExportEvent;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

public class JacksonTest {

    ObjectMapper objectMapper = new ObjectMapper();

   /* @Bean(name = "OBJECT_MAPPER_BEAN")*/
    public ObjectMapper jsonObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
            .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
            .modules(new JSR310Module())
            .build();
    }

    @Test
    public void givenBidirectionRelation_whenSerializing_thenException() throws JsonProcessingException {
        DocumentEntity doc1 = DocumentEntity.builder()
                .name("My Life")
                .contentType("Word")
                .build();

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .publicId(UUID.randomUUID())
                .birthDate(LocalDate.of(1970,1,6))
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

    @Test
    void testSerialization() throws JsonProcessingException {
        String json = "{\"projects\":[{\"publicId\":\"9a03a91d-8593-443f-a652-dd3a00dcfd81\",\"name\":\"New Building\",\"description\":\"New building as residential complex contain 8 flats\",\"budget\":500000.0,\"planedStartDate\":\"2020-10-01\",\"planedFinishDate\":\"2021-10-31\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\",\"projectSteps\":[{\"publicId\":\"7fad1702-2333-4e1d-94f0-f2edc057e898\",\"name\":\"Ground work\",\"description\":\"Prepare the ground and the building base\",\"budget\":50000.0,\"planedStartDate\":\"2020-10-01\",\"planedFinishDate\":\"2020-11-30\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\"},{\"publicId\":\"42467d0d-d341-40c3-b290-ee0cc0b2048e\",\"name\":\"Build walls\",\"description\":\"Build the walls and rooms\",\"budget\":150000.0,\"planedStartDate\":\"2020-12-01\",\"planedFinishDate\":\"2020-02-28\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\"},{\"publicId\":\"1f068ee0-0037-45ba-94bd-7d7bdbeecc07\",\"name\":\"Build roof\",\"description\":\"Build the roof\",\"budget\":150000.0,\"planedStartDate\":\"2020-03-01\",\"planedFinishDate\":\"2020-04-30\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\"}],\"assignedEmployees\":[{\"publicId\":\"05c8b322-b834-4c8e-b4b3-c4d77cb2d9ab\",\"createdAt\":null,\"updatedAt\":null,\"createdBy\":null,\"updatedBy\":null,\"data\":{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthDate\":\"1985-06-23\",\"gender\":\"MALE\",\"email\":\"john.doe@gmail.com\",\"address\":null},\"salary\":null,\"weekendWork\":null,\"traveling\":null,\"documents\":null,\"assignedProjects\":null},{\"publicId\":\"e8ef1a6b-cacd-47cb-ad43-578264e878f3\",\"createdAt\":null,\"updatedAt\":null,\"createdBy\":null,\"updatedBy\":null,\"data\":{\"firstName\":\"Alice\",\"lastName\":\"Brown\",\"birthDate\":\"1995-09-03\",\"gender\":\"FEMALE\",\"email\":\"Alice.Brown@gmail.com\",\"address\":null},\"salary\":null,\"weekendWork\":null,\"traveling\":null,\"documents\":null,\"assignedProjects\":null}]},{\"publicId\":\"af3a6944-a6d1-4e22-b631-f62ac4060d0e\",\"name\":\"Soccer stadium\",\"description\":\"Soccer stadium for world cup 2026\",\"budget\":5.0E7,\"planedStartDate\":\"2020-02-01\",\"planedFinishDate\":\"2026-01-31\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\",\"projectSteps\":[{\"publicId\":\"b87f0a6b-2691-4ea7-824e-14f91185cca5\",\"name\":\"Ground work\",\"description\":\"Prepare the ground and the stadium base\",\"budget\":1500000.0,\"planedStartDate\":\"2020-02-15\",\"planedFinishDate\":\"2020-08-30\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\"},{\"publicId\":\"bf74cc8e-ef3c-4bee-9586-4eb30ccd9fb3\",\"name\":\"Build walls\",\"description\":\"Build the walls and rooms\",\"budget\":1500000.0,\"planedStartDate\":\"2020-12-01\",\"planedFinishDate\":\"2020-02-28\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\"},{\"publicId\":\"0fda6737-4567-4920-a6df-ff2091a0a878\",\"name\":\"Build roof\",\"description\":\"Build the roof\",\"budget\":150000.0,\"planedStartDate\":\"2020-03-01\",\"planedFinishDate\":\"2020-04-30\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\"}],\"assignedEmployees\":[]},{\"publicId\":\"76ca862c-01a7-4fc2-afdc-f1eb49950074\",\"name\":\"Airport Chicago\",\"description\":\"The new Airport of Chicago\",\"budget\":3.0E8,\"planedStartDate\":\"2020-02-01\",\"planedFinishDate\":\"2026-01-31\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\",\"projectSteps\":[{\"publicId\":\"95f2d242-c259-4e7d-a0a5-bb3af6214f2b\",\"name\":\"Ground work\",\"description\":\"Prepare the ground and the stadium base\",\"budget\":1500000.0,\"planedStartDate\":\"2020-02-15\",\"planedFinishDate\":\"2020-08-30\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\"},{\"publicId\":\"41c13efa-f87a-4474-b44d-ba391c587ffa\",\"name\":\"Build walls\",\"description\":\"Build the walls and rooms\",\"budget\":1500000.0,\"planedStartDate\":\"2020-12-01\",\"planedFinishDate\":\"2020-02-28\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\"},{\"publicId\":\"f320658e-dc42-4129-ac06-b54d480ba305\",\"name\":\"Build roof\",\"description\":\"Build the roof\",\"budget\":150000.0,\"planedStartDate\":\"2020-03-01\",\"planedFinishDate\":\"2020-04-30\",\"startDate\":null,\"finishDate\":null,\"status\":\"WAITING\"}],\"assignedEmployees\":[]}]}";
        ProjectExportEvent projectExportEvent = jsonObjectMapper()
            .readValue(json, ProjectExportEvent.class);
    }

}

