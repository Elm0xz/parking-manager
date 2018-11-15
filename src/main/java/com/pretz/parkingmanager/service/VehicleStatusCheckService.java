package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.repository.ParkingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleStatusCheckService {

    private final ParkingSessionRepository parkingSessionRepository;

    public Boolean checkVehicleStatus(String vehicleId) {
        return parkingSessionRepository.existsByVehicleIdAndStopTimeIsNull(vehicleId);
    }
}
