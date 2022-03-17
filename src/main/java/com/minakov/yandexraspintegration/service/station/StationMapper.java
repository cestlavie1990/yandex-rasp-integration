package com.minakov.yandexraspintegration.service.station;

import com.minakov.yandexraspintegration.controller.graphql.type.station.Station;
import com.minakov.yandexraspintegration.model.StationEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StationMapper extends IMapper<StationEntity, Station> {
    StationMapper INSTANCE = Mappers.getMapper(StationMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "settlementId", source = "source.settlement.id")
    })
    Station toDto(final StationEntity source);

    default String map(final UUID id) {
        return id == null ? null : id.toString();
    }
}
