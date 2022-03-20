package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class StringCriteriaValue implements ICriteriaValue<String> {
    @Builder.Default
    @NonNull
    private final List<String> values = new ArrayList<>();

    @Builder.Default
    @NonNull
    private final Boolean inverse = Boolean.FALSE;
}
