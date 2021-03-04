package com.it2go.micro.employeesservice.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.employeesservice.domian.Document;
import com.it2go.micro.employeesservice.persistence.jpa.entities.DocumentEntity;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * created by mmbarek on 02.03.2021.
 */
@DisplayName("Document Mapper Test - ")
class DocumentMapperTest {

  DocumentMapper documentMapper;

  @BeforeEach
  void beforeEach(){
    documentMapper = new DocumentMapperImpl();
  }

  @Test
  void documentEntityToDocument() {
    DocumentEntity documentEntity = DocumentEntity.builder()
        .id(1L)
        .publicId(UUID.randomUUID())
        .contentType("pdf")
        .name("profile")
        .build();

    Document document = documentMapper.documentEntityToDocument(documentEntity);
    assertThat(document).isNotNull();
    assertThat(document.getPublicId()).isEqualTo(documentEntity.getPublicId());
  }

  @Test
  void documentToDocumentEntity() {
    Document document = Document.builder()
        .name("doc1")
        .publicId(UUID.randomUUID())
        .contentType("word")
        .build();

    DocumentEntity documentEntity = documentMapper.documentToDocumentEntity(document);
    assertThat(documentEntity).isNotNull();
    assertThat(documentEntity.getPublicId()).isEqualTo(document.getPublicId());
    assertThat(documentEntity.getName()).isEqualTo(document.getName());
  }

  @Test
  void updateDocumentEntity() {

    DocumentEntity documentEntity = DocumentEntity.builder()
        .id(1L)
        .publicId(UUID.randomUUID())
        .contentType("pdf")
        .name("profile")
        .build();

    Document document = Document.builder()
        .name("doc1")
        .publicId(UUID.randomUUID())
        .contentType("word")
        .build();

    DocumentEntity updateDocumentEntity = documentMapper.updateDocumentEntity(documentEntity, document);

    assertThat(updateDocumentEntity).isNotNull();
    assertThat(updateDocumentEntity.getId()).isEqualTo(documentEntity.getId());
    assertThat(updateDocumentEntity.getPublicId()).isEqualTo(document.getPublicId());
    assertThat(updateDocumentEntity.getName()).isEqualTo(document.getName());
    assertThat(updateDocumentEntity.getContentType()).isEqualTo(document.getContentType());
  }
}
