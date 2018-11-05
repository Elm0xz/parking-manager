package com.pretz.parkingmanager.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PARKING_SESSION")
public class ParkingSession {

    @Id
    @Column(name = "parking_session_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private long id;

    @Column(name = "vehicle_id", updatable = false, nullable = false)
    @Setter
    @Getter
    private String vehicleId;

    @Column(name = "start_time")
    @Getter
    @Setter
    private Timestamp startTime;

    @Column(name = "stop_time")
    @Getter
    @Setter
    private Timestamp stopTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "parking_rate")
    @Getter
    @Setter
    private ParkingRate parkingRate;

}
