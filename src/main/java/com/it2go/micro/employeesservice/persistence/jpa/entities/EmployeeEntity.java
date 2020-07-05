package com.it2go.micro.employeesservice.persistence.jpa.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EMPLOYEE")
public class EmployeeEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "PUBLIC_ID")
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true, fetch = FetchType.LAZY)
    // documents must be moved to another service, not loading as lazy
    private List<DocumentEntity> documents;

    public void addDocument(DocumentEntity documentEntity){
        Objects.requireNonNull(documentEntity);

        documentEntity.setOwner(this);
        if(this.documents == null) this.documents = new ArrayList<>();
        this.documents.add(documentEntity);
    }
}
