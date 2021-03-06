package com.minakov.yandexraspintegration.model.mapper;

import com.minakov.yandexraspintegration.feign.dto.yandex.rasp.stationlist.CountryDto;
import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import com.minakov.yandexraspintegration.util.MapperUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {RegionEntityMapper.class, CodeEmbeddedMapper.class, MapperUtils.class},
        builder = @Builder(disableBuilder = true))
public interface CountryEntityMapper extends IMapper<CountryDto, CountryEntity> {
    CountryEntityMapper INSTANCE = Mappers.getMapper(CountryEntityMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true), @Mapping(target = "code", source = "codes"),
            @Mapping(qualifiedByName = "NullIfBlank", target = "title")
    })
    CountryEntity map(final CountryDto source);

    @AfterMapping
    default void updateRegions(@MappingTarget CountryEntity target) {
        target.getRegions().forEach(r -> r.setCountry(target));
    }
}
