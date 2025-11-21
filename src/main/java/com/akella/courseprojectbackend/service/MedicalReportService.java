package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.ApplicationUtils;
import com.akella.courseprojectbackend.dto.MedicalReportDto;
import com.akella.courseprojectbackend.repository.MedicRepository;
import com.akella.courseprojectbackend.repository.MedicalReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalReportService {
    private final MedicalReportRepository medicalReportRepository;
    private final MedicRepository medicRepository;

    public void create(MedicalReportDto medicalReportDto) {
        Long medicId = medicRepository.findMedicIdByEmail(ApplicationUtils.getEmailFromContext());
        medicalReportRepository.saveEntry(medicalReportDto.accidentId(), medicalReportDto.personId(),
                medicId, medicalReportDto.report());
    }

    public List<MedicalReportDto> getAllByAccidentIds(List<Long> personIds) {
        return medicalReportRepository.findAllByAccidentIdIn(personIds);
    }
}
