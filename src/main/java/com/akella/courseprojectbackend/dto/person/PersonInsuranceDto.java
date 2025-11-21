package com.akella.courseprojectbackend.dto.person;

import com.akella.courseprojectbackend.type.PassportDetails;

public record PersonInsuranceDto(Long id, PassportDetails passportDetails, String patronymic, String surname, String name) implements PersonDto {
    public Long getId() {
        return id;
    }
}
