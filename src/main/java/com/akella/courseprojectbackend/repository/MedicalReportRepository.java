package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.dto.MedicalReportDto;
import com.akella.courseprojectbackend.model.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport, Long> {
    @Modifying
    @Query(value = """
    INSERT INTO medical_report (accident_id, person_id, medic_id, report) VALUES (:accident_id, :person_id, :medic_id,
                                                                                      :report)
    """, nativeQuery = true)
    void saveEntry(@Param("accident_id") Long accidentId, @Param("person_id") Long personId,
                   @Param("medic_id") Long medicId, @Param("report") String report);

    List<MedicalReportDto> findAllByAccidentIdIn(List<Long> accidentIds);
}
