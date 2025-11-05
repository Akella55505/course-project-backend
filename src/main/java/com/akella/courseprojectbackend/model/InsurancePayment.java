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
@Table(name = "insurance_payment")
public class InsurancePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long payment;
    @ManyToOne(optional = false)
    @JoinColumn(name = "insurance_evaluation_id", referencedColumnName = "id", nullable = false)
    private InsuranceEvaluation insuranceEvaluation;
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;
}
