package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.dto.InsurancePaymentDto;
import com.akella.courseprojectbackend.dto.userData.UserInsurancePaymentDto;
import com.akella.courseprojectbackend.model.InsurancePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsurancePaymentRepository extends JpaRepository<InsurancePayment, Long> {
    @Query(value = """
    SELECT new com.akella.courseprojectbackend.dto.userData.UserInsurancePaymentDto(ip.id, ip.payment, ip.insuranceEvaluation.id)
    FROM InsurancePayment ip
    """)
    List<UserInsurancePaymentDto> findAllUserData();

    List<InsurancePaymentDto> findAllByInsuranceEvaluationIdIn(List<Long> insuranceEvaluationIds);

    @Modifying
    @Query(value = """
    INSERT INTO insurance_payment (payment, insurance_evaluation_id, person_id) VALUES (:payment, :insurance_evaluation_id, :person_id)
    """, nativeQuery = true)
    void saveEntry(@Param("payment") Long payment, @Param("insurance_evaluation_id") Long insuranceEvaluationId,
                   @Param("person_id") Long personId);
}
