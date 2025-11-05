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
@Table(name = "violation")
public class Violation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String violation;
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;
    @ManyToOne(optional = false)
    @JoinColumn(name = "accident_id", referencedColumnName = "id", nullable = false)
    private Accident accident;
}
