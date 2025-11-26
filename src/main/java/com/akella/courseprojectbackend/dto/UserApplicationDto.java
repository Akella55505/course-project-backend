package com.akella.courseprojectbackend.dto;

import com.akella.courseprojectbackend.enums.ApplicationStatus;
import com.akella.courseprojectbackend.type.PassportDetails;

import java.sql.Date;
import java.sql.Time;

public record UserApplicationDto(
        PassportDetails passportDetails,
        String patronymic,
        String surname,
        String name,
        String licensePlate,
        Date date,
        Time time,
        String addressStreet,
        String addressNumber) {}
