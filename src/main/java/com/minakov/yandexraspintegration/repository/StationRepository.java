package com.minakov.yandexraspintegration.repository;

import com.minakov.yandexraspintegration.model.StationEntity;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StationRepository extends GenericRepository<StationEntity, UUID>, JpaSpecificationExecutor<StationEntity> {
    Stream<StationEntity> findAllBySettlementIdIn(final Collection<UUID> settlementId);
}
