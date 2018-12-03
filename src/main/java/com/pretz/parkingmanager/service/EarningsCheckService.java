package com.pretz.parkingmanager.service;

import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.EarningsResponseDTO;
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
public class EarningsCheckService {

    private final ParkingSessionRepository parkingSessionRepository;

    public EarningsResponseDTO checkEarnings(Date date) {

        List<ParkingSession> parkingSessionsFinishedOnDate = parkingSessionRepository.findAllByStopTimeBetween(new Timestamp(date.getTime()),
                Timestamp.from(date.toInstant().plus(1, ChronoUnit.DAYS)));

        BigDecimal earningsSum = parkingSessionsFinishedOnDate.stream()
                .map(ParkingSession::getDues)
                .reduce(BigDecimal::add)
                .map(p -> p.setScale(2, RoundingMode.HALF_UP))
                .orElse(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP));

        return EarningsResponseDTO.builder()
                .earnings(earningsSum)
                .build();
    }
}
