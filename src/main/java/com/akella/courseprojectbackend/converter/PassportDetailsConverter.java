package com.akella.courseprojectbackend.converter;

import com.akella.courseprojectbackend.type.PassportDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PassportDetailsConverter implements AttributeConverter<PassportDetails, String> {

    @Override
    public String convertToDatabaseColumn(PassportDetails passportDetails) {
        try { return new ObjectMapper().writeValueAsString(passportDetails); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public PassportDetails convertToEntityAttribute(String s) {
        try { return new ObjectMapper().readValue(s, PassportDetails.class); }
        catch (Exception e) { throw new RuntimeException(e); }
    }
}
