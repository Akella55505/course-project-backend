package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.ApplicationUtils;
import com.akella.courseprojectbackend.dto.accident.AccidentDto;
import com.akella.courseprojectbackend.dto.accident.AccidentPersonDto;
import com.akella.courseprojectbackend.dto.register.AccidentRegisterDto;
import com.akella.courseprojectbackend.dto.userData.UserAccidentDto;
import com.akella.courseprojectbackend.enums.AssessmentStatus;
import com.akella.courseprojectbackend.enums.ConsiderationStatus;
import com.akella.courseprojectbackend.model.Accident;
import com.akella.courseprojectbackend.repository.AccidentRepository;
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
    private final JdbcTemplate jdbcTemplate;

    public List<? extends AccidentDto> gerAllByDateTimeAddress(Date date, Time time, String addressStreet, String addressNumber,
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
}
