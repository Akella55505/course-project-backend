package com.akella.courseprojectbackend.dto.accident;

import com.akella.courseprojectbackend.enums.AccidentRole;
import com.akella.courseprojectbackend.enums.AssessmentStatus;
import com.akella.courseprojectbackend.enums.ConsiderationStatus;
import com.akella.courseprojectbackend.type.Media;

import java.sql.Date;
import java.sql.Time;

public record AccidentPersonDto(Long id, Date date, Media media, String addressStreet, String addressNumber, String causes,
                                AssessmentStatus assessmentStatus, ConsiderationStatus considerationStatus, String type,
                                Time time, AccidentRole accidentRole) implements AccidentDto {
    @Override
    public Long getId() {
        return id;
    }
}
