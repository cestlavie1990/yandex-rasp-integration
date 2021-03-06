package com.minakov.yandexraspintegration.it.helper;

import com.minakov.yandexraspintegration.model.StationEntity;
import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import com.minakov.yandexraspintegration.repository.StationRepository;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StationTestHelper extends AbstractTestHelper<UUID, StationEntity> {
    @NonNull
    private final StationRepository repository;
    @NonNull
    private final SettlementTestHelper settlementTestHelper;

    @Transactional
    @Override
    public @NonNull UUID create() {
        return create(settlementTestHelper.create());
    }

    @Transactional
    public @NonNull UUID create(@NonNull final UUID settlementId) {
        return repository.save(StationEntity.builder()
                .settlement(settlementTestHelper.getEntity(settlementId))
                .code(Default.CODE)
                .title(Default.TITLE.concat(UUID.randomUUID().toString()))
                .direction(Default.DIRECTION)
                .transportType(Default.TRANSPORT_TYPE)
                .stationType(Default.STATION_TYPE)
                .longitude(Default.LONGITUDE)
                .latitude(Default.LATITUDE)
                .build()).getId();
    }

    @Override
    protected @NonNull GenericRepository<StationEntity, UUID> getRepository() {
        return repository;
    }

    public static final class Default {
        private static final String TITLE = "station-title-";

        public static final String DIRECTION = "station-direction";
        public static final String TRANSPORT_TYPE = "station-transport-type";
        public static final String STATION_TYPE = "station-type";
        public static final Double LONGITUDE = 100.501;
        public static final Double LATITUDE = -100.502;
        public static final CodeEmbedded CODE =
                CodeEmbedded.builder().yandexCode("station-yandex-code").esrCode("station-esr-code").build();
    }
}
