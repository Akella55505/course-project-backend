package com.akella.courseprojectbackend.model.view;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Immutable;

@Data
@Entity
@Immutable
@Table(name = "accident_statistics_previous_quarter")
public class AccidentStatisticsPreviousQuarter {
    @Id
    private Long id;
    private String accidentType;
    private Long accidentCount;
    private Long victimCount;
    private Long paymentAmount;
}
