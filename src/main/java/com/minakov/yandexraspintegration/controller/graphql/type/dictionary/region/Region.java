package com.minakov.yandexraspintegration.controller.graphql.type.dictionary.region;

import com.minakov.yandexraspintegration.controller.graphql.type.dictionary.AbstractYandexDictionary;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Region extends AbstractYandexDictionary<String> {
    @NonNull
    private final String countryId;
}
