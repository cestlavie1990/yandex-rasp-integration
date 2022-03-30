package com.minakov.yandexraspintegration.controller.graphql.type.dictionary.country;

import com.minakov.yandexraspintegration.controller.graphql.type.dictionary.AbstractYandexDictionary;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Country extends AbstractYandexDictionary<String> {}
