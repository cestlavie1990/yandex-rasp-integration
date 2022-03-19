package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@Builder
public class StringCriteria implements ICriteria {
    @Nullable
    private final StringCriteriaValue in;
    @Nullable
    private final StringCriteriaValue like;
}
