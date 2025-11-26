package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.UserApplicationDto;
import com.akella.courseprojectbackend.model.UserApplication;
import com.akella.courseprojectbackend.service.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class UserApplicationController {
    private final UserApplicationService userApplicationService;

    @GetMapping
    public ResponseEntity<List<UserApplication>> get(@RequestParam Integer pageIndex) {
        List<UserApplication> responseList;
        try {
            responseList = userApplicationService.find(pageIndex);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(responseList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestParam Boolean declined) {
        try {
            userApplicationService.update(id, declined);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserApplicationDto userApplicationDto) {
        try {
            userApplicationService.create(userApplicationDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
