package com.minakov.yandexraspintegration.repository;

import com.minakov.yandexraspintegration.model.RegionEntity;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RegionRepository extends GenericRepository<RegionEntity, UUID>, JpaSpecificationExecutor<RegionEntity> {
    Stream<RegionEntity> findAllByCountryIdIn(@NonNull Collection<UUID> countryIds);
}
