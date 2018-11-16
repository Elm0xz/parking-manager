package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.exception.EarningsCheckException;
import com.pretz.parkingmanager.repository.ParkingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EarningsService {

    private final ParkingSessionRepository parkingSessionRepository;

    public BigDecimal checkEarnings(Date date) {

        List<ParkingSession> parkingSessionsFinishedOnDate = parkingSessionRepository.findAllByStopTimeBetween(new Timestamp(date.getTime()),
                Timestamp.from(date.toInstant().plus(1, ChronoUnit.DAYS)));

        return parkingSessionsFinishedOnDate.stream()
                .map(ParkingSession::getDues)
                .reduce(BigDecimal::add)
                .map(p -> p.setScale(2, RoundingMode.HALF_UP))
                .orElseThrow(EarningsCheckException::new);
    }
}
