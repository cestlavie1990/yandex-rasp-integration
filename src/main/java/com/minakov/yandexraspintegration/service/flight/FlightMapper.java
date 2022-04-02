package com.minakov.yandexraspintegration.service.flight;

import com.minakov.yandexraspintegration.controller.graphql.type.schedule.TransportType;
import com.minakov.yandexraspintegration.controller.graphql.type.schedule.flight.Flight;
import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation.ScheduleDto;
import com.minakov.yandexraspintegration.service.IMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlightMapper extends IMapper<ScheduleDto, Flight> {
    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "uid", source = "source.thread.uid"),
            @Mapping(target = "title", source = "source.thread.title"),
            @Mapping(target = "number", source = "source.thread.number"),
            @Mapping(target = "vehicle", source = "source.thread.vehicle"),
            @Mapping(target = "transportType", source = "source.thread.transportType")
    })
    Flight map(final ScheduleDto source);

    default TransportType mapTransportType(String name) {
        return name == null ? null : TransportType.byInternalName(name);
    }
}
