package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.dto.InsuranceEvaluationDto;
import com.akella.courseprojectbackend.dto.userData.UserInsuranceEvaluationDto;
import com.akella.courseprojectbackend.enums.AssessmentStatus;
import com.akella.courseprojectbackend.repository.AccidentRepository;
import com.akella.courseprojectbackend.repository.InsuranceEvaluationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceEvaluationService {
    private final InsuranceEvaluationRepository insuranceEvaluationRepository;
    private final AccidentRepository accidentRepository;

    public List<UserInsuranceEvaluationDto> getUserData() {
        return insuranceEvaluationRepository.findAllUserData();
    }

    public List<InsuranceEvaluationDto> getAllByAccidentIds(List<Long> accidentIds) {
        return insuranceEvaluationRepository.findAllByAccidentIdIn(accidentIds);
    }

    @Transactional
    public void create(InsuranceEvaluationDto insuranceEvaluationDto) {
        insuranceEvaluationRepository.saveEntry(insuranceEvaluationDto.conclusion(),
                insuranceEvaluationDto.accidentId(), insuranceEvaluationDto.vehicleId());
        accidentRepository.updateAssessmentStatus(AssessmentStatus.ASSESSED.getStatus(),  insuranceEvaluationDto.accidentId());
    }
}
