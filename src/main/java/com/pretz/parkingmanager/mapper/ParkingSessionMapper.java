package com.pretz.parkingmanager.mapper;

import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.ParkingStartRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingSessionMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ParkingSessionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.addConverter(new ParkingStartRequestToSessionConverter());
    }

    public ParkingSession fromParkingStartRequestDTO(ParkingStartRequestDTO parkingStartRequestDTO) {
        return modelMapper.map(parkingStartRequestDTO, ParkingSession.class);
    }
}
