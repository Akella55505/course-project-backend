package com.akella.courseprojectbackend.dto;

public record InsuranceEvaluationDto(Long id, String conclusion, Long accidentId, Long vehicleId) {
    public Long getId() {
        return id;
    }
}
