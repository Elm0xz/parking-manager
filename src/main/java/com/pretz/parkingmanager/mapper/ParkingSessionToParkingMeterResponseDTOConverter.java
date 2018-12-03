package com.pretz.parkingmanager.mapper;

import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.ParkingMeterResponseDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.sql.Timestamp;

public class ParkingSessionToParkingMeterResponseDTOConverter
        implements Converter<ParkingSession, ParkingMeterResponseDTO> {

    @Override
    public ParkingMeterResponseDTO convert(MappingContext<ParkingSession, ParkingMeterResponseDTO> mappingContext) {

        ParkingSession source = mappingContext.getSource();

        Timestamp mappedTimestamp = (source.getStopTime() == null) ? source.getStartTime() : source.getStopTime();

        return ParkingMeterResponseDTO.builder()
                .vehicleId(source.getVehicleId())
                .parkingSessionId(source.getId())
                .timestamp(mappedTimestamp)
                .build();
    }
}
