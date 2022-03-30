package com.minakov.yandexraspintegration.service.region;

import com.minakov.yandexraspintegration.controller.graphql.input.region.RegionFilter;
import com.minakov.yandexraspintegration.controller.graphql.type.dictionary.region.Region;
import com.minakov.yandexraspintegration.exception.RegionNotFoundException;
import com.minakov.yandexraspintegration.model.RegionEntity;
import com.minakov.yandexraspintegration.repository.GenericRepository;
import com.minakov.yandexraspintegration.repository.RegionRepository;
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
public class RegionService extends AbstractEntityService<UUID, Region, RegionEntity, RegionNotFoundException> {
    @NonNull
    private final RegionRepository repository;

    @Transactional(readOnly = true)
    @NonNull
    public <T> Map<UUID, List<T>> getMapByCountryIdIn(@NonNull final Collection<UUID> countryIds,
            @NonNull final Function<RegionEntity, T> mapper) {
        return repository.findAllByCountryIdIn(countryIds)
                .collect(Collectors.groupingBy(RegionEntity::getCountryId,
                        Collectors.mapping(mapper, Collectors.toList())));
    }

    @Transactional(readOnly = true)
    @NonNull
    public <T> List<T> getAll(@Nullable final RegionFilter filter, @NonNull final Function<RegionEntity, T> mapper) {
        return repository.findAll(new RegionEntitySpecification(filter))
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

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
        return RegionMapper.INSTANCE::map;
    }
}
