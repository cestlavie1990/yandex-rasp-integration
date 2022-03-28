package com.minakov.yandexraspintegration.service.settlement;

import com.minakov.yandexraspintegration.controller.graphql.type.settlement.Settlement;
import com.minakov.yandexraspintegration.exception.SettlementNotFoundException;
import com.minakov.yandexraspintegration.model.SettlementEntity;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import com.minakov.yandexraspintegration.repository.SettlementRepository;
import com.minakov.yandexraspintegration.service.AbstractEntityService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettlementService extends AbstractEntityService<UUID, Settlement, SettlementEntity, SettlementNotFoundException> {
    @NonNull
    private final SettlementRepository repository;

    @Transactional(readOnly = true)
    @NonNull
    public <T> Map<UUID, List<T>> getMapByRegionIdIn(@NonNull final Collection<UUID> regionIds,
            @NonNull final Function<SettlementEntity, T> mapper) {
        return repository.findAllByRegionIdIn(regionIds)
                .collect(Collectors.groupingBy(SettlementEntity::getRegionId,
                        Collectors.mapping(mapper, Collectors.toList())));
    }

    @Override
    protected @NonNull GenericRepository<SettlementEntity, UUID> getRepository() {
        return repository;
    }

    @Override
    protected @NonNull Function<UUID, SettlementNotFoundException> getNoEntityError() {
        return SettlementNotFoundException::new;
    }

    @Override
    public @NonNull Function<SettlementEntity, Settlement> getMapper() {
        return SettlementMapper.INSTANCE::map;
    }
}
