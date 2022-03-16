package com.minakov.yandexraspintegration.it.helper;

import com.minakov.yandexraspintegration.model.CountryEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.repository.CountryRepository;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CountryTestHelper extends AbstractTestHelper<UUID, CountryEntity> {
    @NonNull
    private final CountryRepository repository;

    public static String DEFAULT_TITLE = "countryTitle";
    public static CodeEmbedded DEFAULT_CODE =
            CodeEmbedded.builder().yandexCode("countryYandexCode").esrCode("countryEsrCode").build();

    @Transactional
    @Override
    public @NonNull UUID create() {
        return repository.save(CountryEntity.builder().code(DEFAULT_CODE).title(DEFAULT_TITLE).build()).getId();
    }

    @Override
    protected @NonNull GenericRepository<CountryEntity, UUID> getRepository() {
        return repository;
    }
}
