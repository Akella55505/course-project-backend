package com.akella.courseprojectbackend.dto.userData;

import com.akella.courseprojectbackend.enums.AssessmentStatus;
import com.akella.courseprojectbackend.enums.ConsiderationStatus;

import java.sql.Date;
import java.sql.Time;

public record UserAccidentDto(Long id, Date date, Time time, String addressStreet, String addressNumber,
                              AssessmentStatus assessmentStatus, ConsiderationStatus considerationStatus) {}
