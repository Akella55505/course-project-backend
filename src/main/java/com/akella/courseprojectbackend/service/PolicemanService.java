package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.dto.PolicemanDto;
import com.akella.courseprojectbackend.model.Policeman;
import com.akella.courseprojectbackend.repository.PolicemanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicemanService {
    private final PolicemanRepository policemanRepository;

    public void setEmailByPolicemanId(Long policemanId, String email) {
        policemanRepository.setEmailByPolicemanId(policemanId, email);
    }

    public void create(PolicemanDto policemanDto) {
        Policeman policeman = Policeman.builder().policemanId(policemanDto.policemanId())
                        .name(policemanDto.name()).surname(policemanDto.surname())
                        .patronymic(policemanDto.patronymic()).build();
        policemanRepository.save(policeman);
    }
}
