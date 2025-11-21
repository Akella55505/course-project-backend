package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.dto.CourtDecisionDto;
import com.akella.courseprojectbackend.dto.userData.UserCourtDecisionDto;
import com.akella.courseprojectbackend.enums.ConsiderationStatus;
import com.akella.courseprojectbackend.repository.AccidentRepository;
import com.akella.courseprojectbackend.repository.CourtDecisionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtDecisionService {
    private final CourtDecisionRepository courtDecisionRepository;
    private final AccidentRepository accidentRepository;

    public List<UserCourtDecisionDto> getUserData() {
        return courtDecisionRepository.findAllUserData();
    }

    @Transactional
    public void createCourtDecision(Long accidentId, String decision) {
        courtDecisionRepository.createCourtDecision(accidentId, decision);
        accidentRepository.updateConsiderationStatus(ConsiderationStatus.REVIEWED.getStatus(), accidentId);
    }

    public List<CourtDecisionDto> getAllByAccidentIds(List<Long> accidentIds) {
        return courtDecisionRepository.findAllByAccidentIdIn(accidentIds);
    }
}
