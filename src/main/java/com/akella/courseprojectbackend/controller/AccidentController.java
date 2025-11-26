package com.akella.courseprojectbackend.controller;

import com.akella.courseprojectbackend.ApplicationUtils;
import com.akella.courseprojectbackend.dto.*;
import com.akella.courseprojectbackend.dto.person.PersonDto;
import com.akella.courseprojectbackend.dto.register.AccidentRegisterDto;
import com.akella.courseprojectbackend.dto.report.AccidentReportDto;
import com.akella.courseprojectbackend.dto.userData.*;
import com.akella.courseprojectbackend.dto.accident.AccidentDto;
import com.akella.courseprojectbackend.enums.Role;
import com.akella.courseprojectbackend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

    private final AccidentService accidentService;
    private final VehicleService vehicleService;
    private final AdministrativeDecisionService administrativeDecisionService;
    private final InsuranceEvaluationService insuranceEvaluationService;
    private final InsurancePaymentService insurancePaymentService;
    private final CourtDecisionService courtDecisionService;
    private final AccidentVehicleService accidentVehicleService;
    private final PersonService personService;
    private final MedicalReportService medicalReportService;
    private final ViolationService violationService;

    private static final Set<Role> MEDICAL_ACCESS = EnumSet.of(Role.MEDIC, Role.INSURANCE, Role.COURT, Role.POLICE);
    private static final Set<Role> VEHICLE_ACCESS = EnumSet.of(Role.INSURANCE, Role.COURT, Role.POLICE);
    private static final Set<Role> INSURANCE_ACCESS = EnumSet.of(Role.INSURANCE, Role.POLICE);
    private static final Set<Role> LAW_ACCESS = EnumSet.of(Role.COURT, Role.POLICE);

    public void getDataByRole(Role role, List<Long> accidentIds, AccidentDataDto accidentDataDto) {
        if (MEDICAL_ACCESS.contains(role)) {
            accidentDataDto.setMedicalReportData(medicalReportService.getAllByAccidentIds(accidentIds));
        }
        if (VEHICLE_ACCESS.contains(role)) {
            accidentDataDto.setVehicleData(vehicleService.getAllVehiclesByAccidentIds(accidentIds));
            accidentDataDto.setAccidentVehicleData(accidentVehicleService.getAllByAccidentIds(accidentIds));
        }
        if (INSURANCE_ACCESS.contains(role)) {
            List<InsuranceEvaluationDto> insuranceEvaluationList = insuranceEvaluationService.getAllByAccidentIds(accidentIds);
            List<Long> insuranceEvaluationIds = insuranceEvaluationList.stream().map(InsuranceEvaluationDto::getId).toList();
            accidentDataDto.setInsuranceEvaluationData(insuranceEvaluationList);
            accidentDataDto.setInsurancePaymentData(insurancePaymentService.getAllByInsuranceEvaluationIds(insuranceEvaluationIds));
        }
        if (LAW_ACCESS.contains(role)) {
            accidentDataDto.setAdministrativeDecisionData(administrativeDecisionService.getAllByAccidentIds(accidentIds));
            accidentDataDto.setCourtDecisionData(courtDecisionService.getAllByAccidentIds(accidentIds));
            accidentDataDto.setViolationData(violationService.getAllByAccidentIds(accidentIds));
        }
    }

    @GetMapping
    public ResponseEntity<? extends AccidentDataDto> get(@RequestParam(required = false) Date date,
                                                         @RequestParam(required = false) Time time,
                                                         @RequestParam(required = false) String addressStreet,
                                                         @RequestParam(required = false) String addressNumber,
                                                         @RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String surname,
                                                         @RequestParam(required = false) String patronymic,
                                                         @RequestParam Integer pageIndex) {
        Role role = ApplicationUtils.getRoleFromContext();
        AccidentDataDto response;
        try {
            List<? extends PersonDto> personList = personService.getAllByNameAndSurnameAndPatronymic(name, surname, patronymic);
            List<Long> personIds = personList.isEmpty() ? null : personList.stream().map(PersonDto::getId).toList();
            List<? extends AccidentDto> accidentList = accidentService.getAllByDateTimeAddress(date, time,
                    addressStreet, addressNumber, personIds, pageIndex);
            List<Long> accidentIds =  accidentList.isEmpty() ? null : accidentList.stream().map(AccidentDto::getId).toList();
            personList = personService.getAllByAccidentIds(accidentIds);
            response = AccidentDataDto.builder().accidentData(accidentList).personData(personList).build();
            getDataByRole(role, accidentIds, response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDataDto> getUserData() {
        List<UserAccidentDto> userAccidentData = accidentService.getUserData();
        List<UserVehicleDto> userVehicleData = vehicleService.getUserData();
        List<UserAdministrativeDecisionDto> userAdministrativeDecisionData = administrativeDecisionService.getUserData();
        List<UserInsuranceEvaluationDto> userInsuranceEvaluationData = insuranceEvaluationService.getUserData();
        List<UserInsurancePaymentDto> userInsurancePaymentData = insurancePaymentService.getUserData();
        List<UserCourtDecisionDto> userCourtDecisionData = courtDecisionService.getUserData();
        List<AccidentVehicleDto> userAccidentVehicleData = accidentVehicleService.getUserData();
        List<UserViolationDto> userViolationData = violationService.getUserData();
        UserDataDto userData = new UserDataDto(userAccidentData, userVehicleData, userAdministrativeDecisionData,
                userInsuranceEvaluationData, userInsurancePaymentData, userCourtDecisionData, userAccidentVehicleData,
                userViolationData);
        return ResponseEntity.ok(userData);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<AccidentStatisticsDto>> getStatistics(@RequestParam(required = false) Date startDate,
                                                                     @RequestParam(required = false) Date endDate,
                                                                     @RequestParam(required = false) Time startTime,
                                                                     @RequestParam(required = false) Time endTime,
                                                                     @RequestParam(required = false) String addressStreet,
                                                                     @RequestParam(required = false) String addressNumber,
                                                                     @RequestParam(required = false) String type,
                                                                     @RequestParam Integer pageIndex) {
        List<AccidentStatisticsDto> response;
        try {
            response = accidentService.getStatistics(startDate, endDate, startTime, endTime, addressStreet, addressNumber,
                    type, pageIndex);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistics/streets")
    public ResponseEntity<?> getStreetsStatistics(@RequestParam Integer pageIndex) {
        List<AccidentStreetsStatisticsDto> response;
        try {
            response = accidentService.getStreetsStatistics(pageIndex);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/report")
    public ResponseEntity<AccidentReportDto> generateReport(@RequestParam(required = false) Date startDate,
                                                            @RequestParam(required = false) Date endDate,
                                                            @RequestParam(required = false) Time startTime,
                                                            @RequestParam(required = false) Time endTime,
                                                            @RequestParam(required = false) String addressStreet,
                                                            @RequestParam(required = false) String addressNumber,
                                                            @RequestParam(required = false) String type) {
        AccidentReportDto response = new AccidentReportDto();
        try {
            List<Long> accidentIds = accidentService.getIdsByCriteria(startDate, endDate, startTime, endTime, addressStreet, addressNumber, type);
            accidentService.generateReport(response, accidentIds);
            violationService.generateReport(response, accidentIds);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccidentRegisterDto accidentRegisterDto) {
        try {
            accidentService.create(accidentRegisterDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
