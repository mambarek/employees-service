package com.it2go.micro.employeesservice.persistence.jpa.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DOCUMENT")
public class DocumentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_seq_gen")
    @SequenceGenerator(name = "document_seq_gen", sequenceName = "document_seq", allocationSize = 50)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "PUBLIC_ID", unique = true, nullable = false, updatable = false)
    private UUID publicId;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID", referencedColumnName = "PUBLIC_ID", nullable = false)
    @JsonIgnore
    private EmployeeEntity owner;

    @Basic
    @Column(name = "NAME", length = 100,nullable = false)
    private String name;

    @Basic
    @Column(name = "TYPE", length = 20, nullable = false)
    @NotNull
    private String contentType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    //@Column(name = "CONTENT", columnDefinition="BLOB NOT NULL")
    private byte[] content;
}
