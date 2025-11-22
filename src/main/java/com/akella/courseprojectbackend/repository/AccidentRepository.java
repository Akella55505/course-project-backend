package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.dto.report.AccidentQueryResultDto;
import com.akella.courseprojectbackend.dto.report.AccidentReportDto;
import com.akella.courseprojectbackend.dto.AccidentStatisticsDto;
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

    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.AccidentStatisticsDto(COUNT(a), a.causes)
    FROM Accident a
    WHERE (a.date > :startDate) AND (a.date < :endDate) AND (a.time > :startTime) AND (a.time < :endTime)
    AND (CAST(:addressStreet AS STRING) IS NULL OR a.addressStreet = :addressStreet)
    AND (CAST(:addressNumber AS STRING) IS NULL OR a.addressNumber = :addressNumber)
    AND (CAST(:type AS STRING) IS NULL OR a.type = :type)
    GROUP BY a.causes
    ORDER BY COUNT(a) DESC
    """)
    List<AccidentStatisticsDto> getStatistics(Date startDate, Date endDate, Time startTime, Time endTime, String addressStreet,
                                        String addressNumber, String type, Pageable pageable);

    @Query("""
    SELECT a.id
    FROM Accident a
    WHERE (a.date > :startDate) AND (a.date < :endDate) AND (a.time > :startTime) AND (a.time < :endTime)
    AND (CAST(:addressStreet AS STRING) IS NULL OR a.addressStreet = :addressStreet)
    AND (CAST(:addressNumber AS STRING) IS NULL OR a.addressNumber = :addressNumber)
    AND (CAST(:type AS STRING) IS NULL OR a.type = :type)
    """)
    List<Long> findAllIdsByCriteria(Date startDate, Date endDate, Time startTime, Time endTime, String addressStreet, String addressNumber, String type);

    @Query(value = """
            WITH accident_scope AS (
                    SELECT * FROM accident WHERE id IN :accidentIds
            ),
            total_count AS (
                    SELECT COUNT(*) AS cnt FROM accident_scope
            ),
            report_street AS (
                    SELECT address_street, COUNT(*) AS cnt
                    FROM accident_scope
                    GROUP BY address_street
                    ORDER BY COUNT(*) DESC
            LIMIT 1
            ),
            report_causes AS (
                    SELECT causes, COUNT(*) AS cnt
                    FROM accident_scope
                GROUP BY causes
                ORDER BY COUNT(*) DESC
                LIMIT 1
            ),
            report_type AS (
                SELECT type, COUNT(*) AS cnt
                FROM accident_scope
                GROUP BY type
                ORDER BY COUNT(*) DESC
                LIMIT 1
            ),
            report_daytime AS (
                    SELECT daytime, cnt
                    FROM (
                            SELECT
                                CASE
                                    WHEN EXTRACT(HOUR FROM time) BETWEEN 6 AND 11 THEN 'MORNING'
                                    WHEN EXTRACT(HOUR FROM time) BETWEEN 12 AND 17 THEN 'AFTERNOON'
                                    WHEN EXTRACT(HOUR FROM time) BETWEEN 18 AND 23 THEN 'EVENING'
                                    ELSE 'NIGHT'
                                END AS daytime,
                                COUNT(*) AS cnt
                            FROM accident_scope
                            GROUP BY daytime
                            ORDER BY cnt DESC
                            LIMIT 1
                        ) t
            ),
            report_driver AS (
                    SELECT ap.person_id, COUNT(*) AS cnt
                    FROM accident_scope ascope JOIN accident_person ap ON ascope.id = ap.accident_id
                    WHERE ap.role = 'Винуватець'
                    GROUP BY ap.person_id
                    ORDER BY COUNT(*) DESC
                    LIMIT 1
            )
            SELECT tc.cnt, rs.address_street, rs.cnt, rc.causes, rc.cnt, rt.type, rt.cnt, rd.daytime, rd.cnt, rdr.person_id, rdr.cnt
            FROM total_count tc, report_street rs, report_causes rc, report_type rt, report_daytime rd, report_driver rdr
    """, nativeQuery = true)
    AccidentQueryResultDto generateReport(List<Long> accidentIds);
}
