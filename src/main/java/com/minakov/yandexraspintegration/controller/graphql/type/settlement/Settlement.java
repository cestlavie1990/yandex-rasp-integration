package com.minakov.yandexraspintegration.controller.graphql.type.settlement;

import com.minakov.yandexraspintegration.controller.graphql.type.AbstractYandexRasp;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Settlement extends AbstractYandexRasp<String> {
    @NonNull
    private final String regionId;
}
