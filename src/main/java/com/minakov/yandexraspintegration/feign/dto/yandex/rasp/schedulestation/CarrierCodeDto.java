package com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@Builder
@RequiredArgsConstructor
public class CarrierCodeDto {
    @Nullable
    private final String iata;
    @Nullable
    private final String icao;
    @Nullable
    private final String sirena;
}
