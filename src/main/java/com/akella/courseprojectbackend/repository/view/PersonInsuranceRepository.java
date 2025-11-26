package com.akella.courseprojectbackend.repository.view;

import com.akella.courseprojectbackend.dto.person.PersonAccidentRoleDto;
import com.akella.courseprojectbackend.dto.person.PersonInsuranceDto;
import com.akella.courseprojectbackend.dto.person.PersonMedicDto;
import com.akella.courseprojectbackend.model.view.PersonInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonInsuranceRepository extends JpaRepository<PersonInsurance, Long> {
    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.person.PersonAccidentRoleDto(ap.accident.id,
    new com.akella.courseprojectbackend.dto.person.PersonInsuranceDto(p.id, p.passportDetails, p.name, p.surname, p.patronymic), ap.role)
    FROM Person p
    JOIN AccidentPerson ap ON ap.person.id = p.id
    WHERE ap.accident.id = :accidentId
""")
    List<PersonAccidentRoleDto<PersonInsuranceDto>> findAllByAccidentId(@Param("accidentId") Long accidentId);

    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.person.PersonInsuranceDto(p.id, p.passportDetails, p.name, p.surname, p.patronymic)
    FROM Person p
    JOIN AccidentPerson ap ON ap.person.id = p.id
    WHERE ap.accident.id IN :accidentIds
    """)
    List<PersonInsuranceDto> findAllByAccidentIds(@Param("accidentIds") List<Long> accidentId);

    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.person.PersonMedicDto(p.id, p.name, p.surname, p.patronymic)
    FROM PersonInsurance p
    JOIN AccidentPerson ap ON ap.person.id = p.id
    WHERE p.name = :name AND p.surname = :surname AND p.patronymic = :patronymic
    """)
    List<PersonMedicDto> findAllByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);
}
