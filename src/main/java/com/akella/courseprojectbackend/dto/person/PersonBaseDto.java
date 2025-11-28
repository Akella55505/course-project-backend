package com.akella.courseprojectbackend.dto.person;

import com.akella.courseprojectbackend.type.DriverLicense;
import com.akella.courseprojectbackend.type.PassportDetails;

public record PersonBaseDto(Long id, PassportDetails passportDetails, DriverLicense driverLicense, String name,
                            String surname, String patronymic, String email) implements PersonDto {
    public Long getId() {
        return id;
    }
}
