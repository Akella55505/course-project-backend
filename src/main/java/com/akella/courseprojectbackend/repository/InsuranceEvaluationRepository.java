package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.dto.InsuranceEvaluationDto;
import com.akella.courseprojectbackend.dto.userData.UserInsuranceEvaluationDto;
import com.akella.courseprojectbackend.model.InsuranceEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceEvaluationRepository extends JpaRepository<InsuranceEvaluation, Long> {
    @Query(value = """
    SELECT new com.akella.courseprojectbackend.dto.userData.UserInsuranceEvaluationDto(ie.id, ie.conclusion, ie.accident.id)
    FROM InsuranceEvaluation ie
    """)
    List<UserInsuranceEvaluationDto> findAllUserData();

    List<InsuranceEvaluationDto> findAllByAccidentIdIn(List<Long> accidentIds);

    @Modifying
    @Query(value = """
    INSERT INTO insurance_evaluation (conclusion, accident_id, vehicle_id) VALUES (:conclusion, :accident_id, :vehicle_id)
    """, nativeQuery = true)
    void saveEntry(@Param("conclusion") String conclusion, @Param("accident_id") Long accidentId,
                   @Param("vehicle_id") Long vehicleId);

    @Query("""
    SELECT ie.accident.id FROM InsuranceEvaluation ie WHERE ie.id = :id
    """)
    Long findAccidentIdById(Long id);
}
