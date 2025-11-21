package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.dto.accident.AccidentBaseDto;
import com.akella.courseprojectbackend.dto.accident.AccidentPersonDto;
import com.akella.courseprojectbackend.dto.userData.UserAccidentDto;
import com.akella.courseprojectbackend.model.Accident;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface AccidentRepository extends JpaRepository<Accident, Long> {
    @Query(value = """
    SELECT new com.akella.courseprojectbackend.dto.userData.UserAccidentDto(a.id, a.date, a.time, a.addressStreet, a.addressNumber,
        a.assessmentStatus, a.considerationStatus)
    FROM Accident a
    """)
    List<UserAccidentDto> findAllUserData();

    @Modifying
    @Query(value = """
    UPDATE accident SET assessment_status = :status WHERE id = :acc_id
    """, nativeQuery = true)
    void updateAssessmentStatus(@Param("status") String status, @Param("acc_id") Long accidentId);

    @Modifying
    @Query(value = """
    UPDATE accident SET consideration_status = :status WHERE id = :acc_id
    """, nativeQuery = true)
    void updateConsiderationStatus(@Param("status") String status, @Param("acc_id") Long accidentId);

    @Query(value = """
    SELECT DISTINCT a FROM Accident a
    JOIN AccidentPerson ap ON a.id = ap.accident.id
    WHERE (CAST(:date AS LOCALDATE) IS NULL OR a.date = :date)
      AND (CAST(:time AS LOCALTIME) IS NULL OR a.time = :time)
      AND (CAST(:addressStreet AS STRING) IS NULL OR a.addressStreet = :addressStreet)
      AND (CAST(:addressNumber AS STRING) IS NULL OR a.addressNumber = :addressNumber)
      AND (:personIds IS NULL OR ap.person.id IN :personIds)
    """)
    List<AccidentBaseDto> findAllByDateAndTimeAndAddressStreetAndAddressNumberAndPersonIds(@Param("date") Date date,
                                                                                           @Param("time") Time time,
                                                                                           @Param("addressStreet") String addressStreet,
                                                                                           @Param("addressNumber") String addressNumber,
                                                                                           @Param("personIds") List<Long> personIds,
                                                                                           Pageable pageable);

    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.accident.AccidentPersonDto(a.id, a.date, a.media, a.addressStreet, a.addressNumber,
        a.causes, a.assessmentStatus, a.considerationStatus, a.type, a.time, ap.role)
    FROM Accident a
    JOIN AccidentPerson ap ON a.id = ap.accident.id
    WHERE ap.person.id = :personId
    """)
    List<AccidentPersonDto> findAllByPersonId(Long personId);
}
