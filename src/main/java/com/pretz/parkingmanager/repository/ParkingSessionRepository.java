package com.pretz.parkingmanager.repository;

import com.pretz.parkingmanager.domain.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

    Optional<ParkingSession> findByVehicleIdAndStopTimeIsNull(String vehicleId);

    Optional<ParkingSession> findByVehicleIdAndId(String vehicleId, long id);
}
