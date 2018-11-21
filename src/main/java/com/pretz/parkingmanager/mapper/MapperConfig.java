package com.pretz.parkingmanager.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper requestModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new ParkingStartRequestToSessionConverter());
        return modelMapper;
    }
}
