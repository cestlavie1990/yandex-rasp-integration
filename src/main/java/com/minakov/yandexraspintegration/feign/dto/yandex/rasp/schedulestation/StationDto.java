package com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@RequiredArgsConstructor
public class StationDto {
    @Nullable
    private final String code;
    @Nullable
    private final String stationType;
    @Nullable
    private final String stationTypeName;
    @Nullable
    private final String title;
    @Nullable
    private final String popularTitle;
    @Nullable
    private final String shortTitle;
    @Nullable
    private final String transportType;
    @Nullable
    private final String type;
    @Nullable
    private final StationCodeDto codes;
}
