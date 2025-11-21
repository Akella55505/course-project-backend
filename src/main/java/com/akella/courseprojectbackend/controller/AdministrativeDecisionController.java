package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.AdministrativeDecisionDto;
import com.akella.courseprojectbackend.service.AdministrativeDecisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/administrative-decisions")
public class AdministrativeDecisionController {
    private final AdministrativeDecisionService administrativeDecisionService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AdministrativeDecisionDto administrativeDecision) {
        administrativeDecisionService.create(administrativeDecision.accidentId(),
                administrativeDecision.personId(), administrativeDecision.decision());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
