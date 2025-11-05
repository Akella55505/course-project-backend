package com.akella.courseprojectbackend.converter;

import com.akella.courseprojectbackend.type.DriverLicense;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DriverLicenseConverter implements AttributeConverter<DriverLicense, String> {

    @Override
    public String convertToDatabaseColumn(DriverLicense driverLicense) {
        try { return new ObjectMapper().writeValueAsString(driverLicense); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public DriverLicense convertToEntityAttribute(String s) {
        try { return new ObjectMapper().readValue(s, DriverLicense.class); }
        catch (Exception e) { throw new RuntimeException(e); }
    }
}
