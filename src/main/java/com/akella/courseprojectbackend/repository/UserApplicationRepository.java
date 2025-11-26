package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.enums.ApplicationStatus;
import com.akella.courseprojectbackend.model.UserApplication;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {
    List<UserApplication> findAllByApplicationStatus(ApplicationStatus applicationStatus, Pageable pageable);

    @Modifying
    @Query("""
    UPDATE UserApplication ua SET ua.applicationStatus = :applicationStatus WHERE ua.id = :id
    """)
    void updateStatus(Integer id, ApplicationStatus applicationStatus);

    @Modifying
    @Query(value = """
    INSERT INTO user_application (passport_details, patronymic, surname, name, license_plate, date, time, address_street,
                                      address_number, application_status, sender_email)
        VALUES (:passportDetails, :patronymic, :surname, :name, :licensePlate, :date, :time, :addressStreet, :addressNumber,
                    :applicationStatus, :senderEmail)
    """, nativeQuery = true)
    void saveEntry(String passportDetails, String patronymic, String surname, String name, String licensePlate,
                   Date date, Time time, String addressStreet, String addressNumber, String applicationStatus,
                   String senderEmail);
}
