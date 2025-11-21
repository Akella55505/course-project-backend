package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.ApplicationUtils;
import com.akella.courseprojectbackend.dto.AdministrativeDecisionDto;
import com.akella.courseprojectbackend.dto.userData.UserAdministrativeDecisionDto;
import com.akella.courseprojectbackend.enums.ConsiderationStatus;
import com.akella.courseprojectbackend.repository.AccidentRepository;
import com.akella.courseprojectbackend.repository.AdministrativeDecisionRepository;
import com.akella.courseprojectbackend.repository.PolicemanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministrativeDecisionService {
    private final AdministrativeDecisionRepository administrativeDecisionRepository;
    private final AccidentRepository accidentRepository;
    private final PolicemanRepository policemanRepository;

    public List<UserAdministrativeDecisionDto> getUserData() {
        return administrativeDecisionRepository.findAllUserData();
    }

    @Transactional
    public void create(Long accidentId, Long personId, String decision) {
        Long policemanId = policemanRepository.findPolicemanIdByEmail(ApplicationUtils.getEmailFromContext());
        administrativeDecisionRepository.saveEntry(accidentId, personId, policemanId, decision);
        accidentRepository.updateConsiderationStatus(ConsiderationStatus.SENT.getStatus(), accidentId);
    }

    public List<AdministrativeDecisionDto> getAllByAccidentIds(List<Long> accidentIds) {
        return administrativeDecisionRepository.findAllByAccidentIds(accidentIds);
    }
}
