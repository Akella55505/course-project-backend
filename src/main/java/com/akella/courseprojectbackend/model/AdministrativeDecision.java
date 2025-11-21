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
@Table(name = "administrative_decision")
public class AdministrativeDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String decision;
    @ManyToOne(optional = false)
    @JoinColumn(name = "accident_id", referencedColumnName = "id", nullable = false)
    private Accident accident;
    @ManyToOne(optional = false)
    @JoinColumn(name = "policeman_id", referencedColumnName = "policeman_id", nullable = false)
    private Policeman policeman;
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;
}
