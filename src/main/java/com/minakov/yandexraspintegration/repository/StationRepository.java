package com.minakov.yandexraspintegration.repository;

import com.minakov.yandexraspintegration.model.StationEntity;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

public interface StationRepository extends GenericRepository<StationEntity, UUID> {
    Stream<StationEntity> findAllBySettlementIdIn(final Collection<UUID> settlementId);
}
