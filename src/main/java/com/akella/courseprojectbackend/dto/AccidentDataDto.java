package com.akella.courseprojectbackend.dto;

import com.akella.courseprojectbackend.dto.accident.AccidentDto;
import com.akella.courseprojectbackend.dto.person.PersonDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccidentDataDto {
    private List<? extends AccidentDto> accidentData;
    private List<? extends PersonDto> personData;
    private List<VehicleDto> vehicleData;
    private List<MedicalReportDto> medicalReportData;
    private List<AdministrativeDecisionDto> administrativeDecisionData;
    private List<CourtDecisionDto> courtDecisionData;
    private List<InsuranceEvaluationDto> insuranceEvaluationData;
    private List<InsurancePaymentDto> insurancePaymentData;
    private List<AccidentVehicleDto> accidentVehicleData;
    private List<ViolationDto> violationData;
    private PersonDto person;
}
