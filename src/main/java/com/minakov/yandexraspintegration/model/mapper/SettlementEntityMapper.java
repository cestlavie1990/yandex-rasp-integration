package com.minakov.yandexraspintegration.model.mapper;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.SettlementDto;
import com.minakov.yandexraspintegration.model.SettlementEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {StationEntityMapper.class, CodeEmbeddedMapper.class}, builder = @Builder(disableBuilder = true))
public interface SettlementEntityMapper extends IMapper<SettlementDto, SettlementEntity> {
    SettlementEntityMapper INSTANCE = Mappers.getMapper(SettlementEntityMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true), @Mapping(target = "region", ignore = true),
            @Mapping(target = "regionId", ignore = true), @Mapping(target = "code", source = "codes"),
            @Mapping(target = "title",
                    expression = "java(org.apache.commons.lang3.StringUtils.defaultIfBlank(source.getTitle(), null))")
    })
    SettlementEntity map(final SettlementDto source);

    @AfterMapping
    default void updateStations(@MappingTarget SettlementEntity target) {
        target.getStations().forEach(s -> s.setSettlement(target));
    }
}
