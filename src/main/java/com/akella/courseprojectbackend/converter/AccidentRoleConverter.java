package com.akella.courseprojectbackend.converter;

import com.akella.courseprojectbackend.enums.AccidentRole;
import com.akella.courseprojectbackend.enums.AssessmentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class AccidentRoleConverter implements AttributeConverter<AccidentRole, String> {

    @Override
    public String convertToDatabaseColumn(AccidentRole attribute) {
        return attribute.getRole();
    }

    @Override
    public AccidentRole convertToEntityAttribute(String dbData) {
        return Arrays.stream(AccidentRole.values())
                .filter(e -> e.getRole().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + dbData));
    }
}
