package com.pretz.parkingmanager.mapper;

import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.ParkingMeterResponseDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ParkingSessionToParkingMeterResponseDTOConverter
        implements Converter<ParkingSession, ParkingMeterResponseDTO> {

    @Override
    public ParkingMeterResponseDTO convert(MappingContext<ParkingSession, ParkingMeterResponseDTO> mappingContext) {

        ParkingSession source = mappingContext.getSource();

        return ParkingMeterResponseDTO.builder()
                .vehicleId(source.getVehicleId())
                .parkingSessionId(source.getId())
                .timestamp(source.getStartTime())
                .build();
    }
}
