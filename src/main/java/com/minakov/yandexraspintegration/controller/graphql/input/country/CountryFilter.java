package com.minakov.yandexraspintegration.controller.graphql.input.country;

import com.minakov.yandexraspintegration.controller.graphql.input.filter.BaseFilter;
import com.minakov.yandexraspintegration.controller.graphql.input.filter.StringCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryFilter extends BaseFilter<CountryFilter> {
    @Nullable
    private StringCriteria id;
    @Nullable
    private StringCriteria title;
}
