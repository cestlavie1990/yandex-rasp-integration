package com.minakov.yandexraspintegration.service.station;

import com.minakov.yandexraspintegration.controller.graphql.input.station.StationFilter;
import com.minakov.yandexraspintegration.controller.graphql.type.dictionary.station.Station;
import com.minakov.yandexraspintegration.exception.StationNotFoundException;
import com.minakov.yandexraspintegration.model.StationEntity;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import com.minakov.yandexraspintegration.repository.StationRepository;
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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StationService extends AbstractEntityService<UUID, Station, StationEntity, StationNotFoundException> {
    @NonNull
    private final StationRepository repository;

    @Transactional(readOnly = true)
    @NonNull
    public <T> List<T> getAll(@Nullable final StationFilter filter, @NonNull final Function<StationEntity, T> mapper) {
        return repository.findAll(new StationEntitySpecification(filter))
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @NonNull
    public <T> Map<UUID, List<T>> getMapBySettlementIdIn(@NonNull final Collection<UUID> settlementIds,
            @NonNull final Function<StationEntity, T> mapper) {
        return repository.findAllBySettlementIdIn(settlementIds)
                .collect(Collectors.groupingBy(StationEntity::getSettlementId,
                        Collectors.mapping(mapper, Collectors.toList())));
    }

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
        return StationMapper.INSTANCE::map;
    }
}
