package com.akella.courseprojectbackend.repository;

import com.akella.courseprojectbackend.dto.AccidentVehicleDto;
import com.akella.courseprojectbackend.model.AccidentVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccidentVehicleRepository extends JpaRepository<AccidentVehicle, AccidentVehicle.Id> {
    @Query(value = """
    SELECT new com.akella.courseprojectbackend.dto.AccidentVehicleDto(av.accident.id, av.vehicle.id)
    FROM AccidentVehicle av
    """)
    List<AccidentVehicleDto> findAllUserData();

    List<AccidentVehicleDto> findAllByAccidentIdIn(List<Long> accidentIds);

    List<AccidentVehicleDto> findAllByVehicleIdIn(List<Long> vehicleIds);
}
