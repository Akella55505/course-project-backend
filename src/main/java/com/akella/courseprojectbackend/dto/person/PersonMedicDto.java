package com.akella.courseprojectbackend.dto.person;

public record PersonMedicDto(Long id, String name, String surname, String patronymic) implements PersonDto {
    public Long getId() {
        return id;
    }
}
