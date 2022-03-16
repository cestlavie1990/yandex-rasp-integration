package com.minakov.yandexraspintegration.controller.graphql.type.region;

import com.minakov.yandexraspintegration.controller.graphql.type.AbstractYandexRasp;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Region extends AbstractYandexRasp<String> {
    @NonNull
    private final UUID countryId;
}
