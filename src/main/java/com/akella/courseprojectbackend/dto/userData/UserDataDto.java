package com.akella.courseprojectbackend.dto.userData;

import com.akella.courseprojectbackend.dto.AccidentVehicleDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record UserDataDto(List<UserAccidentDto> userAccidentData, List<UserVehicleDto> userVehicleData,
                          List<UserAdministrativeDecisionDto> userAdministrativeDecisionData,
                          List<UserInsuranceEvaluationDto> userInsuranceEvaluationData,
                          List<UserInsurancePaymentDto> userInsurancePaymentData,
                          List<UserCourtDecisionDto> userCourtDecisionData,
                          List<AccidentVehicleDto> userAccidentVehicleData,
                          List<UserViolationDto> userViolationData) {
}
