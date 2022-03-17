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

    public static String DEFAULT_TITLE = "settlementTitle";
    public static CodeEmbedded DEFAULT_CODE =
            CodeEmbedded.builder().yandexCode("settlementYandexCode").esrCode("settlementEsrCode").build();

    @Transactional
    @Override
    public @NonNull UUID create() {
        final var regionId = regionTestHelper.create();

        return repository.save(SettlementEntity.builder()
                .region(regionTestHelper.getEntity(regionId))
                .code(DEFAULT_CODE)
                .title(DEFAULT_TITLE)
                .build()).getId();
    }

    @Override
    protected @NonNull GenericRepository<SettlementEntity, UUID> getRepository() {
        return repository;
    }
}
