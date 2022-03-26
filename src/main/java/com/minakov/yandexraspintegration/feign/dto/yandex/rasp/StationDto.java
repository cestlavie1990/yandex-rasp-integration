package com.minakov.yandexraspintegration.feign.dto.yandex.rasp;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class StationDto {
    private final CodeDto codes;
    private final String title;
    private final String direction;
    private final String stationType;
    private final String transportType;
    private final Double latitude;
    private final Double longitude;
}