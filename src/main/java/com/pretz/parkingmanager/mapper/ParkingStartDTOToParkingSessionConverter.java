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

        ParkingSession destination = new ParkingSession();
        ParkingStartDTO source = mappingContext.getSource();
        destination.setVehicleId(source.getVehicleId());

        if (source.getParkingRateId() == 1) {
            destination.setParkingRate(ParkingRate.REGULAR);
        } else if (source.getParkingRateId() == 2) {
            destination.setParkingRate(ParkingRate.DISABLED);
        }

        return destination;
    }
}