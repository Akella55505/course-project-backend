package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.enums.Role;
import com.akella.courseprojectbackend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @PatchMapping
    public ResponseEntity<?> updateUserRole(@RequestParam String email, @RequestParam Role role) {
        try {
            adminService.updateUserRole(email, role);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
