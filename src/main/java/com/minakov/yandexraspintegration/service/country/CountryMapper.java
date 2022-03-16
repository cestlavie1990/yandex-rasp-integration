package com.minakov.yandexraspintegration.service.country;

import com.minakov.yandexraspintegration.controller.graphql.type.country.Country;
import com.minakov.yandexraspintegration.model.CountryEntity;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryMapper {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    Country toDto(final CountryEntity source);

    default List<Country> toDto(final List<CountryEntity> sources) {
        return sources.stream().map(this::toDto).collect(Collectors.toList());
    }

    default String map(final UUID id) {
        return id == null ? null : id.toString();
    }
}
