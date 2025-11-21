package com.akella.courseprojectbackend.model;

import com.akella.courseprojectbackend.converter.ApplicationStatusConverter;
import com.akella.courseprojectbackend.converter.PassportDetailsConverter;
import com.akella.courseprojectbackend.enums.ApplicationStatus;
import com.akella.courseprojectbackend.type.PassportDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_application")
public class UserApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "passport_details", nullable = false)
    @Convert(converter = PassportDetailsConverter.class)
    private PassportDetails passportDetails;
    @Column(nullable = false)
    private String patronymic;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String name;
    @Column(name = "license_plate", nullable = false)
    private String licensePlate;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private Time time;
    @Column(name = "address_street", nullable = false)
    private String addressStreet;
    @Column(name = "address_number", nullable = false)
    private String addressNumber;
    @Column(name = "application_status", nullable = false)
    @Convert(converter = ApplicationStatusConverter.class)
    private ApplicationStatus applicationStatus = ApplicationStatus.IN_REVIEW;
    @Column(name = "sender_email", nullable = false)
    private String senderEmail;
}
