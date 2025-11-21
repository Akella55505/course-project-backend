package com.akella.courseprojectbackend.service;

import com.akella.courseprojectbackend.dto.VehicleDto;
import com.akella.courseprojectbackend.dto.userData.UserVehicleDto;
import com.akella.courseprojectbackend.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public List<VehicleDto> getAllVehiclesByAccidentIds(List<Long> accidentIds) {
        return vehicleRepository.findAllByAccidentIds(accidentIds);
    }

    public List<VehicleDto> findAllVehiclesByAccidentId(Long accidentId) {
        return vehicleRepository.findAllByAccidentId(accidentId);
    }

    public List<UserVehicleDto> getUserData() {
        return vehicleRepository.findAllUserData();
    }

    public List<VehicleDto> getAllByPersonId(Long personId) {
        return vehicleRepository.findAllByPersonId(personId);
    }

    public void create(VehicleDto vehicleDto) {
        vehicleRepository.saveEntry(vehicleDto.vin(), vehicleDto.make(), vehicleDto.model(), vehicleDto.licensePlate(),
                vehicleDto.personId());
    }
}
