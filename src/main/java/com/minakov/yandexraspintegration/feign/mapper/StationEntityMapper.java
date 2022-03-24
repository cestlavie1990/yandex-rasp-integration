package com.minakov.yandexraspintegration.feign.mapper;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.StationDto;
import com.minakov.yandexraspintegration.model.StationEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = CodeEmbeddedMapper.class)
public interface StationEntityMapper extends IMapper<StationDto, StationEntity> {
    StationEntityMapper INSTANCE = Mappers.getMapper(StationEntityMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true), @Mapping(target = "settlement", ignore = true),
            @Mapping(target = "settlementId", ignore = true), @Mapping(target = "code", source = "codes")
    })
    StationEntity toDto(final StationDto source);
}
