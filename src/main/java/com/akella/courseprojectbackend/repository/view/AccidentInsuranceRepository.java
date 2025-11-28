package com.akella.courseprojectbackend.repository.view;

import com.akella.courseprojectbackend.dto.accident.AccidentInsuranceDto;
import com.akella.courseprojectbackend.model.view.AccidentInsurance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface AccidentInsuranceRepository extends JpaRepository<AccidentInsurance, Long> {
    @Query("""
    SELECT DISTINCT a FROM AccidentInsurance a
    JOIN AccidentPerson ap ON a.id = ap.accident.id
    WHERE (CAST(:date AS LOCALDATE) IS NULL OR a.date = :date)
      AND (CAST(:time AS LOCALTIME) IS NULL OR a.time = :time)
      AND (CAST(:addressStreet AS STRING) IS NULL OR a.addressStreet = :addressStreet)
      AND (CAST(:addressNumber AS STRING) IS NULL OR a.addressNumber = :addressNumber)
      AND (:personIds IS NULL OR ap.person.id IN :personIds)
    ORDER BY a.date DESC, a.time DESC
    """)
    List<AccidentInsuranceDto> findAllByDateAndTimeAndAddressStreetAndAddressNumberAndPersonIds(@Param("date") Date date,
                                                                                                @Param("time") Time time,
                                                                                                @Param("addressStreet") String addressStreet,
                                                                                                @Param("addressNumber") String addressNumber,
                                                                                                @Param("personIds") List<Long> personIds,
                                                                                                Pageable pageable);
}
