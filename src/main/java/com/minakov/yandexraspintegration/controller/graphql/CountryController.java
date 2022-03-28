package com.minakov.yandexraspintegration.controller.graphql;

import com.minakov.yandexraspintegration.controller.RequestHelper;
import com.minakov.yandexraspintegration.controller.graphql.input.country.CountryFilter;
import com.minakov.yandexraspintegration.controller.graphql.type.country.Country;
import com.minakov.yandexraspintegration.controller.graphql.type.region.Region;
import com.minakov.yandexraspintegration.service.country.CountryMapper;
import com.minakov.yandexraspintegration.service.country.CountryService;
import com.minakov.yandexraspintegration.service.region.RegionMapper;
import com.minakov.yandexraspintegration.service.region.RegionService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CountryController {
    @NonNull
    private final CountryService service;
    @NonNull
    private final RegionService regionService;
    @NonNull
    private final RequestHelper requestHelper;

    @QueryMapping
    public Country country(@Argument final UUID id) {
        return service.getById(id);
    }

    @QueryMapping
    public List<Country> countries(
            @Argument final Map<String, Object> params /*TODO: Fix it, cannot be converted to an object at once.*/) {
        final var filter = requestHelper.toObject(params, "filter", CountryFilter.class);

        return service.getAll(filter, CountryMapper.INSTANCE::map);
    }

    @MutationMapping
    public Integer refreshCountries() {
        return service.refreshAll();
    }

    @BatchMapping
    public Map<Country, List<Region>> regions(final List<Country> countries) {
        final var countryIds = countries.stream().map(c -> UUID.fromString(c.getId())).collect(Collectors.toSet());

        final var regionMap = regionService.getMapByCountryIdIn(countryIds, RegionMapper.INSTANCE::map);

        final var empty = List.<Region>of();

        return countries.stream()
                .collect(Collectors.toMap(Function.identity(),
                        v -> regionMap.getOrDefault(UUID.fromString(v.getId()), empty)));
    }
}
