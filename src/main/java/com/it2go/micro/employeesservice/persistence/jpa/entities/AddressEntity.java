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
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "PUBLIC_ID")
    private UUID publicId;

    @Basic
    @Column(name = "STREET_ONE", nullable = false)
    private String streetOne;

    @Basic
    @Column(name = "BUILDING_NR", nullable = false)
    private String buildingNr;

    @Basic
    @Column(name = "STREET_TWO", nullable = true)
    private String streetTwo;

    @Basic
    @Column(name = "ZIP_CODE", nullable = false)
    private String zipCode;

    @Basic
    @Column(name = "CITY", nullable = false)
    private String city;

    @Basic
    @Column(name = "COUNTRY_CODE", nullable = false)
    private String countryCode;
}
