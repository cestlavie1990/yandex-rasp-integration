package com.minakov.yandexraspintegration.service.settlement;

import com.minakov.yandexraspintegration.controller.graphql.type.settlement.Settlement;
import com.minakov.yandexraspintegration.exception.SettlementNotFoundException;
import com.minakov.yandexraspintegration.model.SettlementEntity;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import com.minakov.yandexraspintegration.repository.SettlementRepository;
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
public class SettlementService extends AbstractEntityService<UUID, Settlement, SettlementEntity, SettlementNotFoundException> {
    @NonNull
    private final SettlementRepository repository;

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
