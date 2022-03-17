package com.minakov.yandexraspintegration.service.station;

import com.minakov.yandexraspintegration.controller.graphql.type.station.Station;
import com.minakov.yandexraspintegration.exception.StationNotFoundException;
import com.minakov.yandexraspintegration.model.StationEntity;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import com.minakov.yandexraspintegration.repository.StationRepository;
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
public class StationService extends AbstractEntityService<UUID, Station, StationEntity, StationNotFoundException> {
    @NonNull
    private final StationRepository repository;

    @Override
    protected @NonNull GenericRepository<StationEntity, UUID> getRepository() {
        return repository;
    }

    @Override
    protected @NonNull Function<UUID, StationNotFoundException> getNoEntityError() {
        return StationNotFoundException::new;
    }

    @Override
    public @NonNull Function<StationEntity, Station> getMapper() {
        return StationMapper.INSTANCE::toDto;
    }
}
