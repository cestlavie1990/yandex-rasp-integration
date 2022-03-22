package com.minakov.yandexraspintegration.repository;

import com.minakov.yandexraspintegration.model.RegionEntity;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.NonNull;

public interface RegionRepository extends GenericRepository<RegionEntity, UUID> {
    Stream<RegionEntity> findAllByCountryId(@NonNull final UUID countryId);
}
