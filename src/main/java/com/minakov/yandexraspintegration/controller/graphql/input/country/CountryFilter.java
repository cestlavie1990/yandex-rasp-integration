package com.minakov.yandexraspintegration.controller.graphql.input.country;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.AbstractFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@Builder
public class CountryFilter extends AbstractFilter<CountryFilter> {
    @Nullable
    private final StringCriteria id;
    @Nullable
    private final StringCriteria title;
}
