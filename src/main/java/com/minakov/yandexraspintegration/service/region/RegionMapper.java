package com.minakov.yandexraspintegration.service.region;

import com.minakov.yandexraspintegration.controller.graphql.type.region.Region;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegionMapper extends IMapper<RegionEntity, Region> {
    RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "countryId", source = "source.country.id")
    })
    Region toDto(final RegionEntity source);

    default String map(final UUID id) {
        return id == null ? null : id.toString();
    }
}
