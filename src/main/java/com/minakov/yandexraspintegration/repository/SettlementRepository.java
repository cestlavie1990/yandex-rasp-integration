package com.minakov.yandexraspintegration.repository;

import com.minakov.yandexraspintegration.model.SettlementEntity;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SettlementRepository extends GenericRepository<SettlementEntity, UUID>, JpaSpecificationExecutor<SettlementEntity> {
    Stream<SettlementEntity> findAllByRegionIdIn(final Collection<UUID> regionId);
}
