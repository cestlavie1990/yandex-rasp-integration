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

    @Transactional
    @Override
    public @NonNull UUID create() {
        return repository.save(CountryEntity.builder()
                .code(Default.CODE)
                .title(Default.TITLE.concat(UUID.randomUUID().toString()))
                .build()).getId();
    }

    @Override
    protected @NonNull GenericRepository<CountryEntity, UUID> getRepository() {
        return repository;
    }

    public static final class Default {
        private static final String TITLE = "country-title-";

        public static final CodeEmbedded CODE =
                CodeEmbedded.builder().yandexCode("country-yandex-code").esrCode("country-esr-code").build();
    }
}
