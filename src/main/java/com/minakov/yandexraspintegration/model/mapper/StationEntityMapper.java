package com.minakov.yandexraspintegration.model.mapper;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.StationDto;
import com.minakov.yandexraspintegration.model.StationEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import com.minakov.yandexraspintegration.util.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CodeEmbeddedMapper.class, MapperUtils.class})
public interface StationEntityMapper extends IMapper<StationDto, StationEntity> {
    StationEntityMapper INSTANCE = Mappers.getMapper(StationEntityMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true), @Mapping(target = "settlement", ignore = true),
            @Mapping(target = "settlementId", ignore = true), @Mapping(target = "code", source = "codes"),
            @Mapping(qualifiedByName = "NullIfBlank", target = "title"),
            @Mapping(qualifiedByName = "NullIfBlank", target = "direction"),
            @Mapping(qualifiedByName = "NullIfBlank", target = "stationType"),
            @Mapping(qualifiedByName = "NullIfBlank", target = "transportType")
    })
    StationEntity map(final StationDto source);
}
