package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.dto.CourtDecisionDto;
import com.akella.courseprojectbackend.dto.userData.UserCourtDecisionDto;
import com.akella.courseprojectbackend.model.CourtDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtDecisionRepository extends JpaRepository<CourtDecision, Long> {
    @Query(value = """
    SELECT new com.akella.courseprojectbackend.dto.userData.UserCourtDecisionDto(cd.id, cd.decision, cd.accident.id)
    FROM CourtDecision cd
    """)
    List<UserCourtDecisionDto> findAllUserData();

    @Modifying
    @Query(value = """
    INSERT INTO court_decision (accident_id, decision) VALUES (:acc_id, :dec)
    """, nativeQuery = true)
    void createCourtDecision(@Param("acc_id") Long accidentId, @Param("dec") String decision);

    List<CourtDecisionDto> findAllByAccidentIdIn(List<Long> accidentIds);
}
