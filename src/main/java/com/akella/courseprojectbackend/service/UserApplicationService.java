package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.enums.ApplicationStatus;
import com.akella.courseprojectbackend.model.UserApplication;
import com.akella.courseprojectbackend.repository.UserApplicationRepository;
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

    public void create(UserApplication userApplication) {
        userApplicationRepository.save(userApplication);
    }

    public void update(Integer id, Boolean declined) {
        ApplicationStatus applicationStatus = declined ? ApplicationStatus.DENIED : ApplicationStatus.PROCESSED;
        userApplicationRepository.updateStatus(id, applicationStatus);
    }
}
