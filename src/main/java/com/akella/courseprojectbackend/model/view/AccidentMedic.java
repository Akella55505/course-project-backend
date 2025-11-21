package com.akella.courseprojectbackend.model.view;

import com.akella.courseprojectbackend.converter.MediaConverter;
import com.akella.courseprojectbackend.type.Media;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.sql.Time;
import java.sql.Date;

@Getter
@Entity
@Immutable
@Table(name = "accident", schema = "medic")
public class AccidentMedic {

    @Id
    private Long id;
    private Date date;
    @Convert(converter = MediaConverter.class)
    private Media media;
    private Time time;
    private String addressStreet;
    private String addressNumber;
}
