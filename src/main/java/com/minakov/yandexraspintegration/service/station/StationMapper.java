package com.minakov.yandexraspintegration.service.station;

import com.minakov.yandexraspintegration.controller.graphql.type.dictionary.station.Station;
import com.minakov.yandexraspintegration.model.StationEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StationMapper extends IMapper<StationEntity, Station> {
    StationMapper INSTANCE = Mappers.getMapper(StationMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "id", expression = "java(source.getId().toString())"),
            @Mapping(target = "settlementId", expression = "java(source.getSettlementId().toString())")
    })
    Station map(final StationEntity source);
}
