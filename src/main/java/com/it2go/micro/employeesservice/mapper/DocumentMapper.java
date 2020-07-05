package com.it2go.micro.employeesservice.mapper;

import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentMapper {

    Document documentEntityToDocument(DocumentEntity documentEntity);
    DocumentEntity documentToDocumentEntity(Document document);
}
