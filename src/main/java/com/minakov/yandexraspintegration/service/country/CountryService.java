package com.minakov.yandexraspintegration.service.country;

import com.minakov.yandexraspintegration.controller.graphql.type.code.Code;
import com.minakov.yandexraspintegration.controller.graphql.type.country.Country;
import com.minakov.yandexraspintegration.exception.CountryNotFoundException;
import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.repository.CountryRepository;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import com.minakov.yandexraspintegration.service.AbstractEntityService;
import java.util.UUID;
import java.util.function.Function;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService extends AbstractEntityService<UUID, Country, CountryEntity, CountryNotFoundException> {
    @NonNull
    private final CountryRepository repository;

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
        return e -> Country.builder()
                .id(e.getId().toString())
                .title(e.getTitle())
                .code(Code.builder().yandexCode(e.getCode().getYandexCode()).esrCode(e.getCode().getEsrCode()).build())
                .build();
    }
}
