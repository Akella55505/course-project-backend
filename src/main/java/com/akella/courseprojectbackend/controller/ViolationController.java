package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.ViolationDto;
import com.akella.courseprojectbackend.service.ViolationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/violations")
public class ViolationController {
    private final ViolationService violationService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ViolationDto violationDto) {
        try {
            violationService.create(violationDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
