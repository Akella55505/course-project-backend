package com.akella.courseprojectbackend.dto.accident;

import com.akella.courseprojectbackend.type.Media;

import java.sql.Date;
import java.sql.Time;

public record AccidentMedicDto(Long id, Date date, Media media, Time time, String addressStreet,
                               String addressNumber) implements AccidentDto {
    public Long getId() {
        return id;
    }
}
