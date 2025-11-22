package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.dto.ViolationDto;
import com.akella.courseprojectbackend.dto.report.ViolationQueryResultDto;
import com.akella.courseprojectbackend.dto.userData.UserViolationDto;
import com.akella.courseprojectbackend.model.Violation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViolationRepository extends JpaRepository<Violation, Long> {
    @Modifying
    @Query(value = """
    INSERT INTO violation (violation, person_id, accident_id) VALUES (:violation, :person_id, :accident_id)
    """, nativeQuery = true)
    void saveEntry(@Param("violation") String violation, @Param("person_id") Long personId, @Param("accident_id") Long accidentId);

    List<ViolationDto> findAllByAccidentIdIn(List<Long> accidentIds);

    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.userData.UserViolationDto(v.id, v.violation, v.accident.id)
    FROM Violation v
    """)
    List<UserViolationDto> findAllUserData();

    List<ViolationDto> findAllByPersonId(Long personid);

    @Query(value = """
            WITH accident_scope AS (
                    SELECT * FROM accident WHERE id IN :accidentIds
            ),
            report_violation AS (
                    SELECT violation, COUNT(*) AS cnt
                    FROM violation v JOIN accident_scope ascope ON v.accident_id = ascope.id
                    GROUP BY violation
                    ORDER BY COUNT(*) DESC
            LIMIT 1
            )
            SELECT rv.violation, rv.cnt FROM report_violation rv
    """, nativeQuery = true)
    ViolationQueryResultDto generateReport(List<Long> accidentIds);
}
