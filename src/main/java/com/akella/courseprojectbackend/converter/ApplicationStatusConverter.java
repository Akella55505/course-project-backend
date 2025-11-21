package com.akella.courseprojectbackend.converter;

import com.akella.courseprojectbackend.enums.ApplicationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class ApplicationStatusConverter implements AttributeConverter<ApplicationStatus, String> {
    @Override
    public String convertToDatabaseColumn(ApplicationStatus attribute) {
        return attribute.getStatus();
    }

    @Override
    public ApplicationStatus convertToEntityAttribute(String dbData) {
        return Arrays.stream(ApplicationStatus.values())
                .filter(e -> e.getStatus().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + dbData));
    }
}
