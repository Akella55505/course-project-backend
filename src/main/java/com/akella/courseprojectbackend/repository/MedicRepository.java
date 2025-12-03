package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.model.Medic;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {
    @Modifying
    @Transactional
    @Query(value = """
    CALL auth.medic_set_email(:medic_id, :email);
    """, nativeQuery = true)
    void setEmailByMedicId(@Param("medic_id") Long medicId, @Param("email") String email);

    @Query("""
    SELECT m.medicId FROM Medic m WHERE m.email = :email
    """)
    Long findMedicIdByEmail(String email);

    @Query("""
    SELECT EXISTS (SELECT 1 FROM Medic m WHERE m.email = :email)
    """)
    Boolean getIsRegistered(String email);
}
