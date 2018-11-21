package com.pretz.parkingmanager.domain;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Entity
@Table(name = "PARKING_SESSION")
public class ParkingSession {

    @Id
    @Column(name = "parking_session_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "vehicle_id", updatable = false, nullable = false)
    @Setter
    private String vehicleId;

    @Column(name = "start_time")
    @Setter
    private Timestamp startTime;

    @Column(name = "stop_time")
    @Setter
    private Timestamp stopTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "parking_rate")
    @Setter
    private ParkingRate parkingRate;

}
