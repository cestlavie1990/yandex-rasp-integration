package com.minakov.yandexraspintegration.controller.graphql.type.country;

import com.minakov.yandexraspintegration.controller.graphql.type.AbstractYandexRasp;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Country extends AbstractYandexRasp<String> {}
