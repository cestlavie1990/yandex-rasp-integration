package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class StringCriteriaValue {
    @NonNull
    private final List<String> values;
    @Builder.Default
    @NonNull
    private final Boolean inverse = Boolean.FALSE;
    @Builder.Default
    @NonNull
    private final CriteriaOperator operator = CriteriaOperator.AND;
}
