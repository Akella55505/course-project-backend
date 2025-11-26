package com.akella.courseprojectbackend.dto.accident;

import com.akella.courseprojectbackend.enums.AssessmentStatus;
import com.akella.courseprojectbackend.type.Media;

import java.sql.Date;
import java.sql.Time;

public record AccidentInsuranceDto(Long id, Media media, String type, Date date, Time time, AssessmentStatus assessmentStatus,
                                   String addressStreet, String addressNumber) implements AccidentDto {
    public Long getId() {
        return id;
    }
}
