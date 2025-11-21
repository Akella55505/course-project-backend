package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.dto.AdministrativeDecisionDto;
import com.akella.courseprojectbackend.dto.userData.UserAdministrativeDecisionDto;
import com.akella.courseprojectbackend.model.AdministrativeDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministrativeDecisionRepository extends JpaRepository<AdministrativeDecision, Long> {
    @Query(value = """
    SELECT new com.akella.courseprojectbackend.dto.userData.UserAdministrativeDecisionDto(ad.id, ad.decision, ad.accident.id)
    FROM AdministrativeDecision ad
    """)
    List<UserAdministrativeDecisionDto> findAllUserData();

    @Modifying
    @Query(value = """
    INSERT INTO administrative_decision (accident_id, person_id, policeman_id, decision) VALUES (:accident_id, :person_id,
                                                                                                     :police_id, :decision)
    """, nativeQuery = true)
    void saveEntry(@Param("accident_id") Long accidentId, @Param("person_id") Long personId,
                   @Param("police_id") Long policemanId, @Param("decision") String decision);

    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.AdministrativeDecisionDto(ad.id, ad.person.id, ad.decision) FROM AdministrativeDecision ad
    WHERE (ad.accident.id IN :accidentIds)
    """)
    List<AdministrativeDecisionDto> findAllByAccidentIds(List<Long> accidentIds);
}
