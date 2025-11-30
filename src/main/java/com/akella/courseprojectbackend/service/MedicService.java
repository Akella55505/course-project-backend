package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.ApplicationUtils;
import com.akella.courseprojectbackend.dto.MedicDto;
import com.akella.courseprojectbackend.model.Medic;
import com.akella.courseprojectbackend.repository.MedicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicService {
    private final MedicRepository medicRepository;

    public void setEmailByMedicId(Long medicId) {
        String email = ApplicationUtils.getEmailFromContext();
        medicRepository.setEmailByMedicId(medicId, email);
    }

    public void create(MedicDto medicDto) {
        Medic medic = Medic.builder().medicId(medicDto.medicId()).name(medicDto.name()).surname(medicDto.surname())
                        .patronymic(medicDto.patronymic()).build();
        medicRepository.save(medic);
    }
}
