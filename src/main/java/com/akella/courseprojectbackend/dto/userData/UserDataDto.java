package com.akella.courseprojectbackend.dto.userData;

import com.akella.courseprojectbackend.dto.AccidentVehicleDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record UserDataDto(List<UserAccidentDto> accidentData, List<UserVehicleDto> vehicleData,
                          List<UserAdministrativeDecisionDto> administrativeDecisionData,
                          List<UserInsuranceEvaluationDto> insuranceEvaluationData,
                          List<UserInsurancePaymentDto> insurancePaymentData,
                          List<UserCourtDecisionDto> courtDecisionData,
                          List<AccidentVehicleDto> accidentVehicleData,
                          List<UserViolationDto> violationData) {
}
