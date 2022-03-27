package com.minakov.yandexraspintegration.controller.graphql.input.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UUIDCriteriaValue implements ICriteriaValue<UUID> {
    @Builder.Default
    @NonNull
    private List<UUID> values = new ArrayList<>();

    @Builder.Default
    @NonNull
    private Boolean inverse = Boolean.FALSE;
}
