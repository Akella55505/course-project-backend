package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.model.Policeman;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicemanRepository extends JpaRepository<Policeman, Long> {
    @Modifying
    @Transactional
    @Query(value = """
    CALL auth.policeman_set_email(:officer_id, :email);
    """, nativeQuery = true)
    void setEmailByPolicemanId(@Param("officer_id") Long officerId, @Param("email") String email);

    @Query("""
    SELECT p.policemanId FROM Policeman p WHERE p.email = :email
    """)
    Long findPolicemanIdByEmail(String email);
}
