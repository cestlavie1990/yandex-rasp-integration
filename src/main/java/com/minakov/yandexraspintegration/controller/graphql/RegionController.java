package com.minakov.yandexraspintegration.controller.graphql;

import com.minakov.yandexraspintegration.controller.graphql.type.country.Country;
import com.minakov.yandexraspintegration.controller.graphql.type.region.Region;
import com.minakov.yandexraspintegration.service.country.CountryService;
import com.minakov.yandexraspintegration.service.region.RegionService;
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

    @QueryMapping
    public Region region(@Argument final UUID id) {
        return service.getById(id);
    }

    @QueryMapping
    public List<Region> regions() {
        return service.getAll();
    }

    @BatchMapping
    public Map<Region, Country> country(final List<Region> regions) {
        final var countries =
                countryService.getAllById(regions.stream().map(Region::getCountryId).collect(Collectors.toSet()))
                        .stream()
                        .collect(Collectors.toMap(Country::getId, Function.identity()));

        return regions.stream()
                .collect(HashMap::new, (m, v) -> m.put(v, countries.get(v.getCountryId().toString())), HashMap::putAll);
    }
}
