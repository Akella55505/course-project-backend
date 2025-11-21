package com.akella.courseprojectbackend.dto.accident;

public sealed interface AccidentDto permits AccidentBaseDto, AccidentInsuranceDto, AccidentMedicDto, AccidentPersonDto {
    Long getId();
}
