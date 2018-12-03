package com.pretz.parkingmanager.mapper;

import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.ParkingMeterResponseDTO;
import com.pretz.parkingmanager.dto.ParkingStartDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingSessionMapper {

    private final ModelMapper modelMapper;

    public ParkingSession fromParkingStartDTO(final ParkingStartDTO parkingStartDTO) {
        return modelMapper.map(parkingStartDTO, ParkingSession.class);
    }

    public ParkingMeterResponseDTO fromParkingSession(final ParkingSession parkingSession) {
        return modelMapper.map(parkingSession, ParkingMeterResponseDTO.class);
    }
}
