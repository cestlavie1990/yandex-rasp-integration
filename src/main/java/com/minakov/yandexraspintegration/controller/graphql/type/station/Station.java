package com.minakov.yandexraspintegration.controller.graphql.type.station;

import com.minakov.yandexraspintegration.controller.graphql.type.AbstractYandexRasp;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Station extends AbstractYandexRasp<String> {
    @NonNull
    private final UUID settlementId;
    @NonNull
    private final String direction;
    @NonNull
    private final String stationType;
    @NonNull
    private final String transportType;
    @NonNull
    private final Double latitude;
    @NonNull
    private final Double longitude;
}
