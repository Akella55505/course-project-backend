package com.akella.courseprojectbackend.repository.view;

import com.akella.courseprojectbackend.dto.AccidentStatisticsPreviousQuarterDto;
import com.akella.courseprojectbackend.model.view.AccidentStatisticsPreviousQuarter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccidentStatisticsPreviousQuarterRepository extends JpaRepository<AccidentStatisticsPreviousQuarter, Long> {
    @Query("""
    SELECT aspq FROM AccidentStatisticsPreviousQuarter aspq
    """)
    List<AccidentStatisticsPreviousQuarterDto> getStatisticsPreviousQuarter(Pageable pageable);
}
