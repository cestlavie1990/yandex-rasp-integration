package com.minakov.yandexraspintegration.controller.graphql.type.region;

import com.minakov.yandexraspintegration.controller.graphql.type.AbstractYandexRasp;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Region extends AbstractYandexRasp<String> {
    @NonNull
    private final UUID countryId;
}
