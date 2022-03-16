package com.minakov.yandexraspintegration.controller.graphql.type.settlement;

import com.minakov.yandexraspintegration.controller.graphql.type.AbstractYandexRasp;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Settlement extends AbstractYandexRasp<String> {
    @NonNull
    private final UUID regionId;
}
