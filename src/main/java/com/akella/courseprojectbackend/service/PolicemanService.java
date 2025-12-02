package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.ApplicationUtils;
import com.akella.courseprojectbackend.dto.PolicemanDto;
import com.akella.courseprojectbackend.model.Policeman;
import com.akella.courseprojectbackend.repository.PolicemanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicemanService {
    private final PolicemanRepository policemanRepository;

    public void setEmailByPolicemanId(Long policemanId) {
        String email = ApplicationUtils.getEmailFromContext();
        policemanRepository.setEmailByPolicemanId(policemanId, email);
    }

    @Transactional
    public void create(PolicemanDto policemanDto) {
        Policeman policeman = Policeman.builder().policemanId(policemanDto.policemanId())
                        .name(policemanDto.name()).surname(policemanDto.surname())
                        .patronymic(policemanDto.patronymic()).build();
        policemanRepository.save(policeman);
        String email = ApplicationUtils.getEmailFromContext();
        policemanRepository.setEmailByPolicemanId(policemanDto.policemanId(), email);
    }
}
