package com.minakov.yandexraspintegration.controller.graphql.type.dictionary.station;

import com.minakov.yandexraspintegration.controller.graphql.type.dictionary.AbstractYandexDictionary;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Station extends AbstractYandexDictionary<String> {
    @NonNull
    private final String settlementId;
    @Nullable
    private final String direction;
    @Nullable
    private final String stationType;
    @Nullable
    private final String transportType;
    @Nullable
    private final Double latitude;
    @Nullable
    private final Double longitude;
}
