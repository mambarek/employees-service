package com.it2go.micro.employeesservice.persistence.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ADDRESS")
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "PUBLIC_ID", unique = true, nullable = false)
    private UUID publicId;

    @Basic
    @Column(name = "STREET_ONE", length = 100,nullable = false)
    private String streetOne;

    @Basic
    @Column(name = "BUILDING_NR", length = 5, nullable = false)
    private String buildingNr;

    @Basic
    @Column(name = "STREET_TWO", length = 100,nullable = true)
    private String streetTwo;

    @Basic
    @Column(name = "ZIP_CODE", length = 10, nullable = false)
    private String zipCode;

    @Basic
    @Column(name = "CITY", length = 100, nullable = false)
    private String city;

    @Basic
    @Column(name = "COUNTRY_CODE", length = 3, nullable = false)
    private String countryCode;
}
