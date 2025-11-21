package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.MedicDto;
import com.akella.courseprojectbackend.service.MedicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medics")
public class MedicController {
    private final MedicService medicService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MedicDto medicDto) {
        try {
            medicService.create(medicDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping
    public ResponseEntity<?> updateEmailByMedicId(@RequestParam Long medicId, @RequestParam String email) {
        try {
            medicService.setEmailByMedicId(medicId, email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
