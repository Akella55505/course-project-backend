package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.MedicDto;
import com.akella.courseprojectbackend.service.MedicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medics")
public class MedicController {
    private final MedicService medicService;

    @GetMapping
    public ResponseEntity<Map<String, Boolean>> getIsRegistered() {
        Boolean response;
        try {
            response = medicService.getIsRegistered();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("isRegistered", response));
    }

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
    public ResponseEntity<?> updateEmailByMedicId(@RequestParam Long medicId) {
        try {
            medicService.setEmailByMedicId(medicId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
