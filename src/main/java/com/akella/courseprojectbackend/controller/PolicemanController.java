package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.PolicemanDto;
import com.akella.courseprojectbackend.service.PolicemanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/policemen")
public class PolicemanController {
    private final PolicemanService policemanService;

    @GetMapping
    public ResponseEntity<Map<String, Boolean>> getIsRegistered() {
        Boolean response;
        try {
            response = policemanService.getIsRegistered();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("isRegistered", response));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PolicemanDto policemanDto) {
        try {
            policemanService.create(policemanDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping
    public ResponseEntity<?> updateEmailByPolicemanId(@RequestParam Long policemanId) {
        try {
            policemanService.setEmailByPolicemanId(policemanId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
