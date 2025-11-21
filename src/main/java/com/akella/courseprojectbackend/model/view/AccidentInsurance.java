package com.akella.courseprojectbackend.model.view;

import com.akella.courseprojectbackend.converter.MediaConverter;
import com.akella.courseprojectbackend.type.Media;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.sql.Date;
import java.sql.Time;

@Getter
@Entity
@Immutable
@Table(name = "accident", schema = "insurance")
public class AccidentInsurance {

    @Id
    private Long id;
    @Convert(converter = MediaConverter.class)
    private Media media;
    private String type;
    private Date date;
    private Time time;
    private String addressStreet;
    private String addressNumber;
}
