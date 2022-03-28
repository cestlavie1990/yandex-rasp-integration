package com.minakov.yandexraspintegration.controller.graphql;

import com.minakov.yandexraspintegration.controller.RequestHelper;
import com.minakov.yandexraspintegration.controller.graphql.input.region.RegionFilter;
import com.minakov.yandexraspintegration.controller.graphql.type.country.Country;
import com.minakov.yandexraspintegration.controller.graphql.type.region.Region;
import com.minakov.yandexraspintegration.controller.graphql.type.settlement.Settlement;
import com.minakov.yandexraspintegration.service.country.CountryService;
import com.minakov.yandexraspintegration.service.region.RegionMapper;
import com.minakov.yandexraspintegration.service.region.RegionService;
import com.minakov.yandexraspintegration.service.settlement.SettlementMapper;
import com.minakov.yandexraspintegration.service.settlement.SettlementService;
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
public class RegionController {
    @NonNull
    private final RegionService service;
    @NonNull
    private final CountryService countryService;
    @NonNull
    private final SettlementService settlementService;
    @NonNull
    private final RequestHelper requestHelper;

    @QueryMapping
    public Region region(@Argument final UUID id) {
        return service.getById(id);
    }

    @QueryMapping
    public List<Region> regions(@Argument final Map<String, Object> params) {
        final var filter = requestHelper.toObject(params, "filter", RegionFilter.class);

        return service.getAll(filter, RegionMapper.INSTANCE::map);
    }

    @BatchMapping
    public Map<Region, Country> country(final List<Region> regions) {
        final var countries = countryService.getAllById(
                        regions.stream().map(r -> UUID.fromString(r.getCountryId())).collect(Collectors.toSet()))
                .stream()
                .collect(Collectors.toMap(Country::getId, Function.identity()));

        return regions.stream()
                .collect(HashMap::new, (m, v) -> m.put(v, countries.get(v.getCountryId())), HashMap::putAll);
    }

    @BatchMapping
    public Map<Region, List<Settlement>> settlements(final List<Region> regions) {
        final var regionIds = regions.stream().map(c -> UUID.fromString(c.getId())).collect(Collectors.toSet());

        final var regionMap = settlementService.getMapByRegionIdIn(regionIds, SettlementMapper.INSTANCE::map);

        final var empty = List.<Settlement>of();

        return regions.stream()
                .collect(Collectors.toMap(Function.identity(),
                        v -> regionMap.getOrDefault(UUID.fromString(v.getId()), empty)));
    }
}
