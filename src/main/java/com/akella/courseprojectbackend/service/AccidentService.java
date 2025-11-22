package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.ApplicationUtils;
import com.akella.courseprojectbackend.dto.report.AccidentQueryResultDto;
import com.akella.courseprojectbackend.dto.report.AccidentReportDto;
import com.akella.courseprojectbackend.dto.AccidentStatisticsDto;
import com.akella.courseprojectbackend.dto.accident.AccidentDto;
import com.akella.courseprojectbackend.dto.accident.AccidentPersonDto;
import com.akella.courseprojectbackend.dto.register.AccidentRegisterDto;
import com.akella.courseprojectbackend.dto.userData.UserAccidentDto;
import com.akella.courseprojectbackend.enums.AssessmentStatus;
import com.akella.courseprojectbackend.enums.ConsiderationStatus;
import com.akella.courseprojectbackend.enums.Daytime;
import com.akella.courseprojectbackend.model.Accident;
import com.akella.courseprojectbackend.repository.AccidentRepository;
import com.akella.courseprojectbackend.repository.PersonRepository;
import com.akella.courseprojectbackend.repository.view.AccidentInsuranceRepository;
import com.akella.courseprojectbackend.repository.view.AccidentMedicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentRepository accidentRepository;
    private final AccidentInsuranceRepository accidentInsuranceRepository;
    private final AccidentMedicRepository accidentMedicRepository;
    private final PersonRepository personRepository;
    private final JdbcTemplate jdbcTemplate;

    public List<? extends AccidentDto> getAllByDateTimeAddress(Date date, Time time, String addressStreet, String addressNumber,
                                                               List<Long> personIds, Integer pageIndex) {
        switch (ApplicationUtils.getRoleFromContext()) {
            case MEDIC -> {
                return accidentMedicRepository.findAllByDateAndTimeAndAddressStreetAndAddressNumberAndPersonIds(date, time, addressStreet,
                        addressNumber, personIds, PageRequest.of(pageIndex, 5));
            }
            case INSURANCE -> {
                return accidentInsuranceRepository.findAllByDateAndTimeAndAddressStreetAndAddressNumberAndPersonIds(date, time, addressStreet,
                        addressNumber, personIds, PageRequest.of(pageIndex, 5));
            }
            default -> {
                return accidentRepository.findAllByDateAndTimeAndAddressStreetAndAddressNumberAndPersonIds(date, time, addressStreet,
                        addressNumber, personIds, PageRequest.of(pageIndex, 5));
            }
        }
    }

    public List<UserAccidentDto> getUserData() {
        return accidentRepository.findAllUserData();
    }

    public List<AccidentPersonDto> getAllByPersonId(Long personId) {
        return accidentRepository.findAllByPersonId(personId);
    }

    @Transactional
    public void create(AccidentRegisterDto accidentRegisterDto) {
        Accident accident = Accident.builder()
                .date(accidentRegisterDto.date())
                .time(accidentRegisterDto.time())
                .media(accidentRegisterDto.media())
                .addressStreet(accidentRegisterDto.addressStreet())
                .addressNumber(accidentRegisterDto.addressNumber())
                .assessmentStatus(AssessmentStatus.IN_REVIEW)
                .considerationStatus(ConsiderationStatus.REGISTERED)
                .causes(accidentRegisterDto.causes())
                .type(accidentRegisterDto.type())
                .build();
        Long accidentId = accidentRepository.save(accident).getId();
        String sql = "INSERT INTO accident_vehicle (accident_id, vehicle_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(sql, accidentRegisterDto.vehicleIds(), 30, (ps, vehicleId) -> {
            ps.setLong(1, accidentId);
            ps.setLong(2, vehicleId);
        });
        sql = "INSERT INTO accident_person (accident_id, person_id, role) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, accidentRegisterDto.personsRoles(), 30, (ps, personsRoles) -> {
            ps.setLong(1, accidentId);
            ps.setLong(2, personsRoles.keySet().iterator().next());
            ps.setString(3, personsRoles.values().iterator().next().getRole());
        });
    }

    private Object[] setNullCriteria(Date startDate, Date endDate, Time startTime, Time endTime) {
        Date newStartDate = startDate == null ? new Date(0) : startDate;
        Date newEndDate = endDate == null ? new Date(System.currentTimeMillis()) : endDate;
        Time newStartTime = startTime == null ? Time.valueOf("00:00:00") : startTime;
        Time newEndTime = endTime == null ? Time.valueOf("23:59:59") : endTime;
        return new Object[]{ newStartDate, newEndDate, newStartTime, newEndTime };
    }

    public List<AccidentStatisticsDto> getStatistics(Date startDate, Date endDate, Time startTime, Time endTime,
                                                     String addressStreet, String addressNumber, String type, Integer pageIndex) {
        Object[] criteria = setNullCriteria(startDate, endDate, startTime, endTime);
        return accidentRepository.getStatistics((Date) criteria[0], (Date) criteria[1], (Time) criteria[2], (Time) criteria[3],
                addressStreet, addressNumber, type, PageRequest.of(pageIndex, 10));
    }

    public List<Long> getIdsByCriteria(Date startDate, Date endDate, Time startTime, Time endTime, String addressStreet,
                                            String addressNumber, String type) {
        Object[] criteria = setNullCriteria(startDate, endDate, startTime, endTime);
        return accidentRepository.findAllIdsByCriteria((Date) criteria[0], (Date) criteria[1], (Time) criteria[2],
                (Time) criteria[3], addressStreet, addressNumber, type);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void generateReport(AccidentReportDto response, List<Long> accidentIds) {
        AccidentQueryResultDto accidentQueryResultDto = accidentRepository.generateReport(accidentIds);
        if (accidentQueryResultDto == null) return;
        response.setReportCount(accidentQueryResultDto.reportCount());
        response.setReportStreet(accidentQueryResultDto.reportStreet());
        response.setStreetCount(accidentQueryResultDto.streetCount());
        response.setReportCauses(accidentQueryResultDto.reportCauses());
        response.setCausesCount(accidentQueryResultDto.causesCount());
        response.setReportType(accidentQueryResultDto.reportType());
        response.setTypeCount(accidentQueryResultDto.typeCount());
        response.setReportDaytime(Daytime.valueOf(accidentQueryResultDto.reportDaytime()));
        response.setDaytimeCount(accidentQueryResultDto.daytimeCount());
        response.setReportDriver(personRepository.findById(accidentQueryResultDto.reportDriverId()).get());
        response.setDriverCount(accidentQueryResultDto.driverCount());
    }
}
