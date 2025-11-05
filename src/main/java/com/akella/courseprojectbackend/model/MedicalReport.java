package com.akella.courseprojectbackend.model;

import com.akella.courseprojectbackend.type.Media;
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
@Table(name = "medical_report")
public class MedicalReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String report;
    @ManyToOne(optional = false)
    @JoinColumn(name = "accident_id", referencedColumnName = "id", nullable = false)
    private Accident accident;
    @ManyToOne(optional = false)
    @JoinColumn(name = "medic_id", referencedColumnName = "license_id", nullable = false)
    private Medic medic;
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;
}
