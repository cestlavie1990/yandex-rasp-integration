package com.minakov.yandexraspintegration.service.region;

import com.minakov.yandexraspintegration.controller.graphql.type.dictionary.region.Region;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegionMapper extends IMapper<RegionEntity, Region> {
    RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "id", expression = "java(source.getId().toString())"),
            @Mapping(target = "countryId", expression = "java(source.getCountryId().toString())")
    })
    Region map(final RegionEntity source);
}
