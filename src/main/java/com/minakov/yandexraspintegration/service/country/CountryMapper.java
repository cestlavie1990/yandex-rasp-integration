package com.minakov.yandexraspintegration.service.country;

import com.minakov.yandexraspintegration.controller.graphql.type.country.Country;
import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryMapper extends IMapper<CountryEntity, Country> {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    @Override
    @Mappings({
            @Mapping(target = "id", expression = "java(source.getId().toString())"),
            @Mapping(target = "title", source = "source.yandexRaspKey.title"),
            @Mapping(target = "code", source = "source.yandexRaspKey.code",
                    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    })
    Country map(final CountryEntity source);
}
