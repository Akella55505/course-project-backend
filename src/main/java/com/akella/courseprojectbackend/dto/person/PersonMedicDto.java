package com.akella.courseprojectbackend.dto.person;

public record PersonMedicDto(Long id, String patronymic, String surname, String name) implements PersonDto {
    public Long getId() {
        return id;
    }
}
