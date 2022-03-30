package com.minakov.yandexraspintegration.controller.graphql.type.schedule.carrier;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@Builder
public class Airline implements ICarrier {
    @Nullable
    private final Integer code;
    @Nullable
    private final String name;
    @Nullable
    private final String iataCode;
    @Nullable
    private final String icaoCode;
    @Nullable
    private final String sirenaCode;
}
