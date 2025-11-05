package com.akella.courseprojectbackend.model;

import com.akella.courseprojectbackend.converter.MediaConverter;
import com.akella.courseprojectbackend.enums.AssessmentStatus;
import com.akella.courseprojectbackend.enums.ConsiderationStatus;
import com.akella.courseprojectbackend.type.Media;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accident")
public class Accident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Date date;
    @Convert(converter = MediaConverter.class)
    private Media media;
    @Column(name = "address_street", nullable = false)
    private String addressStreet;
    @Column(name = "address_number", nullable = false)
    private Integer addressNumber;
    @Column(nullable = false)
    private String causes;
    @Column(name = "assessment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssessmentStatus assessment_status = AssessmentStatus.IN_REVIEW;
    @Column(name = "consideration_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConsiderationStatus consideration_status = ConsiderationStatus.REGISTERED;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "time", nullable = false)
    private Time time;
}
