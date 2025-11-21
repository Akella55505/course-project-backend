package com.akella.courseprojectbackend.converter;

import com.akella.courseprojectbackend.enums.AssessmentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class AssessmentStatusConverter implements AttributeConverter<AssessmentStatus, String> {
    @Override
    public String convertToDatabaseColumn(AssessmentStatus attribute) {
        return attribute.getStatus();
    }

    @Override
    public AssessmentStatus convertToEntityAttribute(String dbData) {
        return Arrays.stream(AssessmentStatus.values())
                .filter(e -> e.getStatus().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + dbData));
    }
}
