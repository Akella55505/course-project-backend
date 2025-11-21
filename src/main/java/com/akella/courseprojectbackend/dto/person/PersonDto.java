package com.akella.courseprojectbackend.dto.person;

public sealed interface PersonDto permits PersonBaseDto, PersonInsuranceDto, PersonMedicDto, PersonAccidentRoleDto {
    Long getId();
}
