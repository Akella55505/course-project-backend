package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.dto.AccidentDataDto;
import com.akella.courseprojectbackend.dto.AccidentVehicleDto;
import com.akella.courseprojectbackend.dto.VehicleDto;
import com.akella.courseprojectbackend.dto.ViolationDto;
import com.akella.courseprojectbackend.dto.accident.AccidentPersonDto;
import com.akella.courseprojectbackend.dto.person.PersonBaseDto;
import com.akella.courseprojectbackend.model.Person;
import com.akella.courseprojectbackend.service.*;
import com.akella.courseprojectbackend.type.PassportDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;
    private final VehicleService vehicleService;
    private final AccidentService accidentService;
    private final AccidentVehicleService accidentVehicleService;
    private final ViolationService violationService;

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody Person person) {
        Long personId;
        try {
            personId = personService.createPerson(person);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(Map.of("id", personId));
    }

    @GetMapping
    public ResponseEntity<Person> getOneByPassportDetails(@RequestBody PassportDetails passportDetails) {
        Person person;
        try {
            person = personService.getOneByPassportDetails(passportDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(person);
    }

    @PatchMapping
    public ResponseEntity<?> updateEmailByPassportDetails(@RequestBody PassportDetails passportDetails, @RequestParam String email) {
        try {
            personService.updateEmailByPassportDetails(passportDetails, email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccidentDataDto> getData(@PathVariable Long id) {
        PersonBaseDto person = personService.getById(id);
        if (person == null) return ResponseEntity.notFound().build();
        List<VehicleDto> vehicles = vehicleService.getAllByPersonId(id);
        List<Long> vehicleIds = vehicles.stream().map(VehicleDto::id).toList();
        List<AccidentPersonDto> accidents = accidentService.getAllByPersonId(id);
        List<AccidentVehicleDto> accidentVehicle = accidentVehicleService.getAllByVehicleIds(vehicleIds);
        List<ViolationDto> violations = violationService.getAllByPersonid(id);
        AccidentDataDto response = AccidentDataDto.builder()
                .person(person)
                .vehicleData(vehicles)
                .accidentData(accidents)
                .accidentVehicleData(accidentVehicle)
                .violationData(violations)
                .build();
        return ResponseEntity.ok(response);
    }
}
