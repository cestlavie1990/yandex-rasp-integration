package com.minakov.yandexraspintegration.model.mapper;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.SettlementDto;
import com.minakov.yandexraspintegration.model.SettlementEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import com.minakov.yandexraspintegration.util.MapperUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {StationEntityMapper.class, CodeEmbeddedMapper.class, MapperUtils.class},
        builder = @Builder(disableBuilder = true))
public interface SettlementEntityMapper extends IMapper<SettlementDto, SettlementEntity> {
    SettlementEntityMapper INSTANCE = Mappers.getMapper(SettlementEntityMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true), @Mapping(target = "region", ignore = true),
            @Mapping(target = "regionId", ignore = true), @Mapping(target = "code", source = "codes"),
            @Mapping(qualifiedByName = "NullIfBlank", target = "title")
    })
    SettlementEntity map(final SettlementDto source);

    @AfterMapping
    default void updateStations(@MappingTarget SettlementEntity target) {
        target.getStations().forEach(s -> s.setSettlement(target));
    }
}
