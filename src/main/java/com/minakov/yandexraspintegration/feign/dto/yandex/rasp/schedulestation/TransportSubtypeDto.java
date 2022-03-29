package com.minakov.yandexraspintegration.feign.dto.yandex.rasp.schedulestation;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class TransportSubtypeDto {
    @NonNull
    private final String color;
    @NonNull
    private final String code;
    @NonNull
    private final String title;
}
