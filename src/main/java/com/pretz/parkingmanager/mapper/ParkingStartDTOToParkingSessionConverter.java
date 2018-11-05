package com.pretz.parkingmanager.mapper;

import com.pretz.parkingmanager.domain.ParkingRate;
import com.pretz.parkingmanager.domain.ParkingSession;
import com.pretz.parkingmanager.dto.ParkingStartDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ParkingStartDTOToParkingSessionConverter
        implements Converter<ParkingStartDTO, ParkingSession> {

    @Override
    public ParkingSession convert(final MappingContext<ParkingStartDTO, ParkingSession> mappingContext) {

        ParkingStartDTO source = mappingContext.getSource();
        ParkingSession.ParkingSessionBuilder destination = ParkingSession.builder()
                .vehicleId(source.getVehicleId());

        if (source.getParkingRateId() == 1) {
            destination.parkingRate(ParkingRate.REGULAR);
        } else if (source.getParkingRateId() == 2) {
            destination.parkingRate(ParkingRate.DISABLED);
        } else
            destination.parkingRate(ParkingRate.NONE);

        return destination.build();
    }
}