package com.minakov.yandexraspintegration.controller.graphql;

import com.minakov.yandexraspintegration.controller.RequestHelper;
import com.minakov.yandexraspintegration.controller.graphql.input.settlement.SettlementFilter;
import com.minakov.yandexraspintegration.controller.graphql.type.region.Region;
import com.minakov.yandexraspintegration.controller.graphql.type.settlement.Settlement;
import com.minakov.yandexraspintegration.controller.graphql.type.station.Station;
import com.minakov.yandexraspintegration.service.region.RegionService;
import com.minakov.yandexraspintegration.service.settlement.SettlementMapper;
import com.minakov.yandexraspintegration.service.settlement.SettlementService;
import com.minakov.yandexraspintegration.service.station.StationMapper;
import com.minakov.yandexraspintegration.service.station.StationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SettlementController {
    @NonNull
    private final SettlementService service;
    @NonNull
    private final RegionService regionService;
    @NonNull
    private final StationService stationService;
    @NonNull
    private final RequestHelper requestHelper;

    @QueryMapping
    public Settlement settlement(@Argument final UUID id) {
        return service.getById(id);
    }

    @QueryMapping
    public List<Settlement> settlements(@Argument final Map<String, Object> params) {
        final var filter = requestHelper.toObject(params, "filter", SettlementFilter.class);

        return service.getAll(filter, SettlementMapper.INSTANCE::map);
    }

    @BatchMapping
    public Map<Settlement, Region> region(final List<Settlement> settlements) {
        final var regions = regionService.getAllById(
                        settlements.stream().map(s -> UUID.fromString(s.getRegionId())).collect(Collectors.toSet()))
                .stream()
                .collect(Collectors.toMap(Region::getId, Function.identity()));

        return settlements.stream()
                .collect(HashMap::new, (m, v) -> m.put(v, regions.get(v.getRegionId())), HashMap::putAll);
    }

    @BatchMapping
    public Map<Settlement, List<Station>> stations(final List<Settlement> settlements) {
        final var settlementIds = settlements.stream().map(c -> UUID.fromString(c.getId())).collect(Collectors.toSet());

        final var regionMap = stationService.getMapBySettlementIdIn(settlementIds, StationMapper.INSTANCE::map);

        final var empty = List.<Station>of();

        return settlements.stream()
                .collect(Collectors.toMap(Function.identity(),
                        v -> regionMap.getOrDefault(UUID.fromString(v.getId()), empty)));
    }
}
