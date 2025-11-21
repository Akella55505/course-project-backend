package com.akella.courseprojectbackend.model;

import com.akella.courseprojectbackend.converter.DriverLicenseConverter;
import com.akella.courseprojectbackend.converter.PassportDetailsConverter;
import com.akella.courseprojectbackend.type.DriverLicense;
import com.akella.courseprojectbackend.type.PassportDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "passport_details", nullable = false, unique = true)
    @Convert(converter = PassportDetailsConverter.class)
    private PassportDetails passportDetails;
    @Column(name = "driver_license", unique = true)
    @Convert(converter = DriverLicenseConverter.class)
    private DriverLicense driverLicense;
    @Column(nullable = false)
    private String patronymic;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String email = null;
}
