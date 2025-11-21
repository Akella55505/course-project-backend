package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.dto.InsurancePaymentDto;
import com.akella.courseprojectbackend.dto.userData.UserInsurancePaymentDto;
import com.akella.courseprojectbackend.enums.AssessmentStatus;
import com.akella.courseprojectbackend.repository.AccidentRepository;
import com.akella.courseprojectbackend.repository.InsuranceEvaluationRepository;
import com.akella.courseprojectbackend.repository.InsurancePaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsurancePaymentService {
    private final InsurancePaymentRepository insurancePaymentRepository;
    private final AccidentRepository accidentRepository;
    private final InsuranceEvaluationRepository insuranceEvaluationRepository;

    public List<UserInsurancePaymentDto> getUserData() {
        return insurancePaymentRepository.findAllUserData();
    }

    public List<InsurancePaymentDto> getAllByInsuranceEvaluationIds(List<Long> insuranceEvaluationIds) {
        return insurancePaymentRepository.findAllByInsuranceEvaluationIdIn(insuranceEvaluationIds);
    }

    @Transactional
    public void create(InsurancePaymentDto insurancePaymentDto) {
        insurancePaymentRepository.saveEntry(insurancePaymentDto.payment(), insurancePaymentDto.insuranceEvaluationId(),
                insurancePaymentDto.personId());
        Long accidentId = insuranceEvaluationRepository.findAccidentIdById(insurancePaymentDto.insuranceEvaluationId());
        accidentRepository.updateAssessmentStatus(AssessmentStatus.AGREED.getStatus(), accidentId);
    }
}
