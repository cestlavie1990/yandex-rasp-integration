package com.minakov.yandexraspintegration.it.helper;

import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.model.embedded.YandexRaspKey;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import com.minakov.yandexraspintegration.repository.RegionRepository;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RegionTestHelper extends AbstractTestHelper<UUID, RegionEntity> {
    @NonNull
    private final RegionRepository repository;
    @NonNull
    private final CountryTestHelper countryTestHelper;

    @Transactional
    @Override
    public @NonNull UUID create() {
        final var countryId = countryTestHelper.create();

        return repository.save(RegionEntity.builder()
                .country(countryTestHelper.getEntity(countryId))
                .yandexRaspKey(YandexRaspKey.builder()
                        .code(Default.CODE)
                        .title(Default.TITLE.concat(UUID.randomUUID().toString()))
                        .build())
                .build()).getId();
    }

    @Override
    protected @NonNull GenericRepository<RegionEntity, UUID> getRepository() {
        return repository;
    }

    public static final class Default {
        private static final String TITLE = "region-title-";

        public static final CodeEmbedded CODE =
                CodeEmbedded.builder().yandexCode("region-yandex-code").esrCode("region-esr-code").build();
    }
}
