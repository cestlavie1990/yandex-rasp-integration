package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StringCriteriaValue implements ICriteriaValue<String> {
    @Builder.Default
    @NonNull
    private List<String> values = new ArrayList<>();

    @Builder.Default
    @NonNull
    private Boolean inverse = Boolean.FALSE;
}
