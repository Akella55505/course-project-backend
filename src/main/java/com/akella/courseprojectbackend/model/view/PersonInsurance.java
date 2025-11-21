package com.akella.courseprojectbackend.model.view;

import com.akella.courseprojectbackend.converter.PassportDetailsConverter;
import com.akella.courseprojectbackend.type.PassportDetails;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Getter
@Entity
@Immutable
@Table(name = "person", schema = "insurance")
public class PersonInsurance {
    @Id
    private Long id;
    @Convert(converter = PassportDetailsConverter.class)
    private PassportDetails passportDetails;
    private String patronymic;
    private String surname;
    private String name;
}
