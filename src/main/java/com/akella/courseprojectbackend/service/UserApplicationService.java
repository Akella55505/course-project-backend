package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.ApplicationUtils;
import com.akella.courseprojectbackend.converter.PassportDetailsConverter;
import com.akella.courseprojectbackend.dto.UserApplicationDto;
import com.akella.courseprojectbackend.enums.ApplicationStatus;
import com.akella.courseprojectbackend.model.UserApplication;
import com.akella.courseprojectbackend.repository.UserApplicationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserApplicationService {
    private final UserApplicationRepository userApplicationRepository;


    public List<UserApplication> find(Integer pageIndex) {
        return userApplicationRepository.findAllByApplicationStatus(ApplicationStatus.IN_REVIEW, PageRequest.of(pageIndex, 5));
    }

    @Transactional
    public void create(UserApplicationDto userApplicationDto) {
        String senderEmail = ApplicationUtils.getEmailFromContext();
        PassportDetailsConverter passportDetailsConverter = new PassportDetailsConverter();
        String passportDetailsJson = passportDetailsConverter.convertToDatabaseColumn(userApplicationDto.passportDetails());
        userApplicationRepository.saveEntry(passportDetailsJson, userApplicationDto.patronymic(),
                userApplicationDto.surname(), userApplicationDto.name(), userApplicationDto.licensePlate(),
                userApplicationDto.date(), userApplicationDto.time(), userApplicationDto.addressStreet(),
                userApplicationDto.addressNumber(), ApplicationStatus.IN_REVIEW.getStatus(), senderEmail);
    }

    @Transactional
    public void update(Integer id, Boolean declined) {
        ApplicationStatus applicationStatus = declined ? ApplicationStatus.DENIED : ApplicationStatus.PROCESSED;
        userApplicationRepository.updateStatus(id, applicationStatus);
    }
}
