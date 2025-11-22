package com.akella.courseprojectbackend.dto.report;

import com.akella.courseprojectbackend.enums.Daytime;
import com.akella.courseprojectbackend.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccidentReportDto {
    private Long reportCount;
    private String reportStreet;
    private Long streetCount;
    private String reportCauses;
    private Long causesCount;
    private String reportType;
    private Long typeCount;
    private Daytime reportDaytime;
    private Long daytimeCount;
    private String reportViolation;
    private Long violationCount;
    private Person reportDriver;
    private Long driverCount;
}
