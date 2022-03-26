package com.minakov.yandexraspintegration.model.mapper;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.RegionDto;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import com.minakov.yandexraspintegration.util.MapperUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SettlementEntityMapper.class, CodeEmbeddedMapper.class, MapperUtils.class},
        builder = @Builder(disableBuilder = true))
public interface RegionEntityMapper extends IMapper<RegionDto, RegionEntity> {
    RegionEntityMapper INSTANCE = Mappers.getMapper(RegionEntityMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true), @Mapping(target = "country", ignore = true),
            @Mapping(target = "countryId", ignore = true), @Mapping(target = "code", source = "codes"),
            @Mapping(qualifiedByName = "NullIfBlank", target = "title")
    })
    RegionEntity map(final RegionDto source);

    @AfterMapping
    default void updateSettlements(@MappingTarget RegionEntity target) {
        target.getSettlements().forEach(r -> r.setRegion(target));
    }
}
