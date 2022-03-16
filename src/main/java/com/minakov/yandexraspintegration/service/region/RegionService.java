package com.minakov.yandexraspintegration.service.region;

import com.minakov.yandexraspintegration.controller.graphql.type.region.Region;
import com.minakov.yandexraspintegration.exception.RegionNotFoundException;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import com.minakov.yandexraspintegration.repository.RegionRepository;
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
public class RegionService extends AbstractEntityService<UUID, Region, RegionEntity, RegionNotFoundException> {
    @NonNull
    private final RegionRepository repository;

    @Override
    protected @NonNull GenericRepository<RegionEntity, UUID> getRepository() {
        return repository;
    }

    @Override
    protected @NonNull Function<UUID, RegionNotFoundException> getNoEntityError() {
        return RegionNotFoundException::new;
    }

    @Override
    public @NonNull Function<RegionEntity, Region> getMapper() {
        return RegionMapper.INSTANCE::toDto;
    }
}
