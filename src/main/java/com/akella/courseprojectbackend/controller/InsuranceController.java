package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.InsuranceEvaluationDto;
import com.akella.courseprojectbackend.dto.InsurancePaymentDto;
import com.akella.courseprojectbackend.service.InsuranceEvaluationService;
import com.akella.courseprojectbackend.service.InsurancePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/insurance")
public class InsuranceController {
    private final InsuranceEvaluationService insuranceEvaluationService;
    private final InsurancePaymentService insurancePaymentService;

    @PostMapping("/evaluations")
    public ResponseEntity<?> createEvaluation(@RequestBody InsuranceEvaluationDto insuranceEvaluationDto) {
        try {
            insuranceEvaluationService.create(insuranceEvaluationDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/payments")
    public ResponseEntity<?> createPayment(@RequestBody InsurancePaymentDto insurancePaymentDto) {
        try {
            insurancePaymentService.create(insurancePaymentDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
