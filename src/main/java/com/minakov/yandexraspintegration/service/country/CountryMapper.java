package com.minakov.yandexraspintegration.service.country;

import com.minakov.yandexraspintegration.controller.graphql.type.country.Country;
import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.service.IMapper;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryMapper extends IMapper<CountryEntity, Country> {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    default String map(final UUID id) {
        return id == null ? null : id.toString();
    }
}
