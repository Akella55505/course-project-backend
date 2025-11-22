package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.dto.ViolationDto;
import com.akella.courseprojectbackend.dto.report.AccidentReportDto;
import com.akella.courseprojectbackend.dto.report.ViolationQueryResultDto;
import com.akella.courseprojectbackend.dto.userData.UserViolationDto;
import com.akella.courseprojectbackend.repository.ViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViolationService {
    private final ViolationRepository violationRepository;

    public void create(ViolationDto violationDto) {
        violationRepository.saveEntry(violationDto.violation(), violationDto.personId(), violationDto.accidentId());
    }

    public List<ViolationDto> getAllByAccidentIds(List<Long> accidentIds) {
        return violationRepository.findAllByAccidentIdIn(accidentIds);
    }

    public List<UserViolationDto> getUserData() {
        return violationRepository.findAllUserData();
    }

    public List<ViolationDto> getAllByPersonid(Long personId) {
        return violationRepository.findAllByPersonId(personId);
    }

    public void generateReport(AccidentReportDto response, List<Long> accidentIds) {
        ViolationQueryResultDto violationQueryResultDto = violationRepository.generateReport(accidentIds);
        if (violationQueryResultDto == null) return;
        response.setReportViolation(violationQueryResultDto.reportViolation());
        response.setViolationCount(violationQueryResultDto.violationCount());
    }
}
