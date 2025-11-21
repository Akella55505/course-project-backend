package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.dto.AccidentVehicleDto;
import com.akella.courseprojectbackend.repository.AccidentVehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccidentVehicleService {
    private final AccidentVehicleRepository accidentVehicleRepository;

    public List<AccidentVehicleDto> getUserData() {
        return accidentVehicleRepository.findAllUserData();
    }

    public List<AccidentVehicleDto> getAllByAccidentIds(List<Long> accidentIds) {
        return accidentVehicleRepository.findAllByAccidentIdIn(accidentIds);
    }

    public List<AccidentVehicleDto> getAllByVehicleIds(List<Long> vehicleIds) {
        return accidentVehicleRepository.findAllByVehicleIdIn(vehicleIds);
    }
}
