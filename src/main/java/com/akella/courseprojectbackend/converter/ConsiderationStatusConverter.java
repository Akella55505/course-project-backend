package com.akella.courseprojectbackend.converter;

import com.akella.courseprojectbackend.enums.ConsiderationStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class ConsiderationStatusConverter implements AttributeConverter<ConsiderationStatus, String> {

    @Override
    public String convertToDatabaseColumn(ConsiderationStatus attribute) {
        return attribute.getStatus();
    }

    @Override
    public ConsiderationStatus convertToEntityAttribute(String dbData) {
        return Arrays.stream(ConsiderationStatus.values())
                .filter(e -> e.getStatus().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + dbData));
    }
}
