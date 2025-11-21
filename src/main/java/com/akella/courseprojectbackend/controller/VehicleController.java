package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.VehicleDto;
import com.akella.courseprojectbackend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<VehicleDto>> get(@RequestParam Long accidentId) {
        List<VehicleDto> responseList;
        try {
            responseList = vehicleService.findAllVehiclesByAccidentId(accidentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody VehicleDto vehicleDto) {
        try {
            vehicleService.create(vehicleDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
