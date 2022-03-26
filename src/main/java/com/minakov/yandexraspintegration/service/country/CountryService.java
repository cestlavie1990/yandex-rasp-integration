package com.minakov.yandexraspintegration.service.country;

import com.minakov.yandexraspintegration.config.properties.YandexRaspProperties;
import com.minakov.yandexraspintegration.controller.graphql.input.country.CountryFilter;
import com.minakov.yandexraspintegration.controller.graphql.type.country.Country;
import com.minakov.yandexraspintegration.exception.CountryNotFoundException;
import com.minakov.yandexraspintegration.feign.YandexRaspClient;
import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.mapper.CountryEntityMapper;
import com.minakov.yandexraspintegration.repository.CountryRepository;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import com.minakov.yandexraspintegration.service.AbstractEntityService;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService extends AbstractEntityService<UUID, Country, CountryEntity, CountryNotFoundException> {
    @NonNull
    private final CountryRepository repository;

    @NonNull
    private final YandexRaspClient yandexRaspClient;
    @NonNull
    private final YandexRaspProperties yandexRaspProperties;

    @Transactional(readOnly = true)
    @NonNull
    public <T> List<T> getAll(@Nullable final CountryFilter filter, @NonNull Function<CountryEntity, T> mapper) {
        return repository.findAll(new CountryEntitySpecification(filter))
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional
    @NonNull
    public Integer refreshAll() {
        final var response = yandexRaspClient.getStationList(yandexRaspProperties.getApiKey(), null, null);

        repository.deleteAll();

        final var entities = CountryEntityMapper.INSTANCE.map(response.getCountries());

        return repository.saveAll(entities).size();
    }

    @Override
    protected @NonNull GenericRepository<CountryEntity, UUID> getRepository() {
        return repository;
    }

    @Override
    protected @NonNull Function<UUID, CountryNotFoundException> getNoEntityError() {
        return CountryNotFoundException::new;
    }

    @Override
    public @NonNull Function<CountryEntity, Country> getMapper() {
        return CountryMapper.INSTANCE::map;
    }
}
