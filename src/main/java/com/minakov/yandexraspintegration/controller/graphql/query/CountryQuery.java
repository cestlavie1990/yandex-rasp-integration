package com.minakov.yandexraspintegration.controller.graphql.query;

import com.minakov.yandexraspintegration.controller.graphql.type.country.Country;
import com.minakov.yandexraspintegration.service.country.CountryService;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CountryQuery {
    @NonNull
    private final CountryService service;

    @QueryMapping
    public Country country(@Argument final UUID id) {
        return service.getById(id);
    }

    @QueryMapping
    public List<Country> countries() {
        return service.getAll();
    }
}
