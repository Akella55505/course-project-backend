package com.akella.courseprojectbackend.dto;

public record VehicleDto(Long id, String vin, String make, String model, String licensePlate, Long personId) {}
