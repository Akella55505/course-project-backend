package com.akella.courseprojectbackend.model.view;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Getter
@Entity
@Immutable
@Table(name = "person", schema = "medic")
public class PersonMedic {
    @Id
    private Long id;
    private String patronymic;
    private String surname;
    private String name;
}
