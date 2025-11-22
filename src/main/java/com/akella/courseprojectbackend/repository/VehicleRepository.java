package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.dto.VehicleDto;
import com.akella.courseprojectbackend.dto.userData.UserVehicleDto;
import com.akella.courseprojectbackend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query(value = """
    SELECT v.id, v.vin, v.model, v.make, v.license_plate, v.person_id FROM Vehicle v
    JOIN accident_vehicle av ON av.vehicle_id = v.id
    WHERE av.accident_id = :accidentId
    """, nativeQuery = true)
    List<VehicleDto> findAllByAccidentId(@Param("accidentId") Long accidentId);

    @Query("""
    SELECT new com.akella.courseprojectbackend.dto.userData.UserVehicleDto(v.id, v.vin, v.licensePlate, v.make, v.model)
    FROM Vehicle v
    """)
    List<UserVehicleDto> findAllUserData();

    @Query(value = """
    SELECT new com.akella.courseprojectbackend.dto.VehicleDto(v.id, v.vin, v.model, v.make, v.licensePlate, v.person.id) FROM Vehicle v
    JOIN AccidentVehicle av ON av.vehicle.id = v.id
    WHERE (av.accident.id IN :accidentIds)
    """)
    List<VehicleDto> findAllByAccidentIds(List<Long> accidentIds);

    List<VehicleDto> findAllByPersonId(Long personId);

    @SuppressWarnings("SpringDataModifyingAnnotationMissing")
    @Query(value = """
    INSERT INTO vehicle (vin, make, model, license_plate, person_id) VALUES (:vin, :make, :model, :license_plate,
                                                                                 :person_id)
    RETURNING id
    """, nativeQuery = true)
    Long saveEntry(@Param("vin") String vin, @Param("make") String make, @Param("model") String model,
                   @Param("license_plate") String licensePlate, @Param("person_id") Long personId);
}
