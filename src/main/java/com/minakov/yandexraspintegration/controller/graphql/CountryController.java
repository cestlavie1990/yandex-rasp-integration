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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
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

    @SchemaMapping
    public List<Region> regions(final Country country) {
        return regionService.getAllByCountryId(UUID.fromString(country.getId()), RegionMapper.INSTANCE::map);
    }
}
