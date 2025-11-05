package com.akella.courseprojectbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccidentController {

    @GetMapping("/accidents")
    public String getAccidents(){
        return "Test";
    }
}
