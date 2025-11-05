package com.akella.courseprojectbackend.converter;

import com.akella.courseprojectbackend.type.Media;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MediaConverter implements AttributeConverter<Media, String> {

    @Override
    public String convertToDatabaseColumn(Media media) {
        try { return new ObjectMapper().writeValueAsString(media); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public Media convertToEntityAttribute(String s) {
        try { return new ObjectMapper().readValue(s, Media.class); }
        catch (Exception e) { throw new RuntimeException(e); }
    }
}
