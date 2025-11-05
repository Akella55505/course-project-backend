package com.akella.courseprojectbackend.model;

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
@Table(name = "insurance_evaluation")
public class InsuranceEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String conclusion;
    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    private Vehicle vehicle;
    @ManyToOne(optional = false)
    @JoinColumn(name = "accident_id", referencedColumnName = "id", nullable = false)
    private Accident accident;
}
