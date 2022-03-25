package com.minakov.yandexraspintegration.it.helper;

import com.minakov.yandexraspintegration.model.SettlementEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import com.minakov.yandexraspintegration.repository.SettlementRepository;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SettlementTestHelper extends AbstractTestHelper<UUID, SettlementEntity> {
    @NonNull
    private final SettlementRepository repository;
    @NonNull
    private final RegionTestHelper regionTestHelper;

    @Transactional
    @Override
    public @NonNull UUID create() {
        final var regionId = regionTestHelper.create();

        return repository.save(SettlementEntity.builder()
                .region(regionTestHelper.getEntity(regionId))
                .code(Default.CODE)
                .title(Default.TITLE.concat(UUID.randomUUID().toString()))
                .build()).getId();
    }

    @Override
    protected @NonNull GenericRepository<SettlementEntity, UUID> getRepository() {
        return repository;
    }

    public static final class Default {
        private static final String TITLE = "settlement-title-";

        public static final CodeEmbedded CODE =
                CodeEmbedded.builder().yandexCode("settlement-yandex-code").esrCode("settlement-esr-code").build();
    }
}
