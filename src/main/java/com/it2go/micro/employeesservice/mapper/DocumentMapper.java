package com.it2go.micro.employeesservice.mapper;

import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface DocumentMapper {

    Document documentEntityToDocument(DocumentEntity documentEntity);
    DocumentEntity documentToDocumentEntity(Document document);
    DocumentEntity updateDocumentEntity(@MappingTarget DocumentEntity documentEntity, Document document);
}
