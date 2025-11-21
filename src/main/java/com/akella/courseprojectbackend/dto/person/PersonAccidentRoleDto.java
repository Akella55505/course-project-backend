package com.akella.courseprojectbackend.dto.person;

import com.akella.courseprojectbackend.enums.AccidentRole;

public record PersonAccidentRoleDto<T extends PersonDto>(Long accidentId, T person, AccidentRole accidentRole) implements PersonDto {
    public Long getId() {
        return person.getId();
    }
}
