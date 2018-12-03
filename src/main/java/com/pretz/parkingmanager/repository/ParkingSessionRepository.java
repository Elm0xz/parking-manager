package com.pretz.parkingmanager.repository;

import com.pretz.parkingmanager.domain.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

    Optional<ParkingSession> findByVehicleIdAndStopTimeIsNull(String vehicleId);

    Optional<ParkingSession> findByVehicleIdAndIdAndStopTimeIsNull(String vehicleId, long id);

    Optional<ParkingSession> findByIdAndStopTimeIsNotNull(long id);

    Boolean existsByVehicleIdAndStopTimeIsNull(String vehicleId);

    List<ParkingSession> findAllByStopTimeIsContainingAndDuesIsNotNull(Date date);

    List<ParkingSession> findAllByStopTime(Date date);

    List<ParkingSession> findAllByStopTimeIsContaining(Date date);

    List<ParkingSession> findAllByStopTimeBetween(Date from, Date to);
}
