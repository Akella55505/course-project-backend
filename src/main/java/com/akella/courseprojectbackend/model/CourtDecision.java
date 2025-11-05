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
@Table(name = "court_decision")
public class CourtDecision {

    @Id
    @Column(name = "accident_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "accident_id",
            foreignKey = @ForeignKey(name = "fk_court_decision_accident"))
    private Accident accident;

    @Column(nullable = false)
    private String decision;
}
