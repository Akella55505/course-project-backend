package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.dto.person.PersonAccidentRoleDto;
import com.akella.courseprojectbackend.dto.person.PersonBaseDto;
import com.akella.courseprojectbackend.dto.person.PersonMedicDto;
import com.akella.courseprojectbackend.model.Person;
import com.akella.courseprojectbackend.type.PassportDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Modifying
    @Transactional
    @Query(value = """
    CALL auth.person_set_email(:passport_details, :email);
    """, nativeQuery = true)
    void setEmailByPassportDetails(@Param("passport_details") String passportDetails, @Param("email") String email);

    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.person.PersonAccidentRoleDto(ap.accident.id,
    new com.akella.courseprojectbackend.dto.person.PersonBaseDto(p.id, p.passportDetails, p.driverLicense, p.name, p.surname, p.patronymic, p.email), ap.role)
    FROM Person p
    JOIN AccidentPerson ap ON ap.person.id = p.id
    WHERE ap.accident.id = :accidentId
    """)
    List<PersonAccidentRoleDto<PersonBaseDto>> findAllByAccidentId(@Param("accidentId") Long accidentId);

    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.person.PersonAccidentRoleDto(ap.accident.id,
    new com.akella.courseprojectbackend.dto.person.PersonBaseDto(p.id, p.passportDetails, p.driverLicense, p.name, p.surname, p.patronymic, p.email), ap.role)
    FROM Person p
    JOIN AccidentPerson ap ON ap.person.id = p.id
    WHERE ap.accident.id IN :accidentIds
    """)
    List<PersonAccidentRoleDto<PersonBaseDto>> findAllByAccidentIds(@Param("accidentIds") List<Long> accidentId);

    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.person.PersonMedicDto(p.id, p.name, p.surname, p.patronymic)
    FROM Person p
    JOIN AccidentPerson ap ON ap.person.id = p.id
    WHERE p.name = :name AND p.surname = :surname AND p.patronymic = :patronymic
    """)
    List<PersonMedicDto> findAllByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);

    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.person.PersonBaseDto(p.id, p.passportDetails, p.driverLicense, p.name,
        p.surname, p.patronymic, p.email)
    FROM Person p
        WHERE p.id = :id
    """)
    PersonBaseDto findByPersonId(Long id);

    Person findByPassportDetails(PassportDetails passportDetails);
}
