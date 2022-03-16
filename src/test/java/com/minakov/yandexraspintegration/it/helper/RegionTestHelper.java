package com.minakov.yandexraspintegration.it.helper;

import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
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

    public static String DEFAULT_TITLE = "regionTitle";
    public static CodeEmbedded DEFAULT_CODE =
            CodeEmbedded.builder().yandexCode("regionYandexCode").esrCode("regionEsrCode").build();

    @Transactional
    @Override
    public @NonNull UUID create() {
        final var countryId = countryTestHelper.create();

        return repository.save(RegionEntity.builder()
                .country(countryTestHelper.getEntity(countryId))
                .code(DEFAULT_CODE)
                .title(DEFAULT_TITLE)
                .build()).getId();
    }

    @Override
    protected @NonNull GenericRepository<RegionEntity, UUID> getRepository() {
        return repository;
    }
}
