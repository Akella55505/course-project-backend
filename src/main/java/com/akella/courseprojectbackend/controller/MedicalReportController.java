package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.MedicalReportDto;
import com.akella.courseprojectbackend.service.MedicalReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medical-reports")
public class MedicalReportController {
    private final MedicalReportService medicalReportService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MedicalReportDto medicalReportDto) {
        try {
            medicalReportService.create(medicalReportDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
