package com.it2go.micro.employeesservice.persistence.jpa.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROJECT")
public class ProjectEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq_gen")
  @SequenceGenerator(name = "project_seq_gen", sequenceName = "project_seq", allocationSize = 50)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

    @Column(name = "PUBLIC_ID", unique = true, nullable = false)
    private UUID publicId;

    @Basic
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 500)
    private String description;

    @Basic
    @Column(name = "PS_DATE", columnDefinition = "DATE")
    private LocalDate planedStartDate;

    @Basic
    @Column(name = "PF_DATE", columnDefinition = "DATE")
    private LocalDate planedFinishDate;

    @Basic
    @Column(name = "S_DATE", columnDefinition = "DATE")
    private LocalDate startDate;

    @Basic
    @Column(name = "F_DATE", columnDefinition = "DATE")
    private LocalDate finishDate;

    @Basic
    @Column(name = "STATUS", length = 20)
    private String status;

  @ManyToMany
  @JoinTable(
      name="PROJECT_EMPLOYEES",
      joinColumns = @JoinColumn( name="PROJECT_PUB_ID", referencedColumnName = "PUBLIC_ID"),
      inverseJoinColumns = @JoinColumn( name="EMPLOYEE_PUB_ID", referencedColumnName = "PUBLIC_ID")
  )
  private List<EmployeeEntity> assignedEmployees;
}
