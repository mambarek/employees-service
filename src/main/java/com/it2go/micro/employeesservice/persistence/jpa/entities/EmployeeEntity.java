package com.it2go.micro.employeesservice.persistence.jpa.entities;

import com.it2go.micro.projectmanagement.domain.Project;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EMPLOYEE")
public class EmployeeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq_gen")
    @SequenceGenerator(name = "employee_seq_gen", sequenceName = "employee_seq", allocationSize = 50)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "PUBLIC_ID", unique = true, nullable = false, updatable = false)
    private UUID publicId;

    @Basic
    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String firstName;

    @Basic
    @Column(name = "LAST_NAME", nullable = false, length = 100)
    private String lastName;

    @Basic
    @Column(name = "BIRTH_DATE", columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(name = "GENDER", length = 10)
    private String gender;

    @Column(name = "EMAIL")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "PUBLIC_ID", name = "ADDR_ID")
    private AddressEntity address;

    @Basic
    @Column(name = "SALARY")
    private Double salary;

    @Column(name = "WEEKEND_WORK")
    private boolean weekendWork;

    @Column(name = "TRAVELING")
    private boolean traveling;

    @Basic
    @Column(name = "CREATED_AT", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    @Basic
    @Column(name = "UPDATED_AT", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updatedAt;

    @Basic
    @Column(name = "CREATED_BY")
    private UUID createdBy;

    @Basic
    @Column(name = "UPDATED_BY")
    private UUID updatedBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DocumentEntity> documents;

    public void addDocument(DocumentEntity documentEntity){
        Objects.requireNonNull(documentEntity);

        documentEntity.setOwner(this);
        // attention lazy loading
        if(this.getDocuments() == null) this.documents = new ArrayList<>();
        this.documents.add(documentEntity);
    }

/*    @ManyToMany
    @JoinTable(
        name="PROJECT_EMPLOYEES",
        joinColumns = @JoinColumn( name="EMPLOYEE_PUB_ID", referencedColumnName = "PUBLIC_ID"),
        inverseJoinColumns = @JoinColumn( name="PROJECT_PUB_ID", referencedColumnName = "PUBLIC_ID")
    )*/
    @ManyToMany(mappedBy = "assignedEmployees")
    private List<ProjectEntity> assignedProjects = new ArrayList<>();
}
