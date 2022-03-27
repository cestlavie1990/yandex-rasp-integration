package com.minakov.yandexraspintegration.controller.graphql.input.country;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.IFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.UUIDCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryFilter implements IFilter<CountryFilter> {
    @Nullable
    private UUIDCriteria id;
    @Nullable
    private StringCriteria title;
}
