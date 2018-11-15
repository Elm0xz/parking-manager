package com.pretz.parkingmanager.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "PARKING_SESSION")
public final class ParkingSession {

    @Id
    @Column(name = "parking_session_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private long id;

    @Column(name = "vehicle_id", updatable = false, nullable = false)
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
    private ParkingRate parkingRate;
}
