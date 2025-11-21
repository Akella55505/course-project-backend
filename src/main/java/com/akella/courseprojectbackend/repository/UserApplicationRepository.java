package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.enums.ApplicationStatus;
import com.akella.courseprojectbackend.model.UserApplication;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {
    List<UserApplication> findAllByApplicationStatus(ApplicationStatus applicationStatus, Pageable pageable);

    @Modifying
    @Query("""
    UPDATE UserApplication ua SET ua.applicationStatus = :applicationStatus WHERE ua.id = :id
    """)
    void updateStatus(Integer id, ApplicationStatus applicationStatus);
}
